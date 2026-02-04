/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import List from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FDS_EVENT} from '@liferay/frontend-data-set-web';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

export const UPDATE_TASK_HISTORY = 'cmp-update-task-history';

enum EventType {
	ADD = 'ADD',
	CMP_ADD_ASSET = 'CMP_ADD_ASSET',
	CMP_REMOVE_ASSET = 'CMP_REMOVE_ASSET',
	UPDATE = 'UPDATE',
}

type Creator = {
	additionalName: string;
	contentType: string;
	externalReferenceCode: string;
	familyName: string;
	givenName: string;
	id: number;
	image?: string;
	name: string;
};

type AuditFieldChange = {
	name: string;
	newValue: any;
	oldValue: any;
};

type AuditEvent = {
	auditFieldChanges?: AuditFieldChange[];
	creator: Creator;
	dateCreated: string;
	eventType: EventType;
};

type Data = {
	auditEvents: AuditEvent[];
};

const RELATED_ASSETS_SECTION =
	'com.liferay.site.cms.site.initializer-relatedAssetsSection';

const FIELD_NAME: {[key: string]: string} = {
	assignTo: Liferay.Language.get('assignee'),
	description: Liferay.Language.get('description'),
	dueDate: Liferay.Language.get('due-date'),
	state: Liferay.Language.get('state'),
	title: Liferay.Language.get('title'),
};

function joinWithAnd(items: string[]) {
	if (!items?.length) {
		return '';
	}

	return new Intl.ListFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		style: 'long',
		type: 'conjunction',
	}).format(items);
}

function TaskHistoryItem({auditEvent}: {auditEvent: AuditEvent}) {
	const [showDetails, setShowDetails] = useState(false);

	const getAuditEventLabel = (auditEvent: AuditEvent) => {
		if (auditEvent.eventType === EventType.ADD) {
			return sub(Liferay.Language.get('x-created-a-x'), [
				<strong key="creatorName">{auditEvent.creator?.name}</strong>,
				<strong key="type">{Liferay.Language.get('task')}</strong>,
			]);
		}
		else if (auditEvent.eventType === EventType.CMP_ADD_ASSET) {
			return sub(Liferay.Language.get('x-added-the-asset-x'), [
				<strong key="creatorName">{auditEvent.creator?.name}</strong>,
				<strong key="changedField">
					{auditEvent.auditFieldChanges?.[0].name}
				</strong>,
			]);
		}
		else if (auditEvent.eventType === EventType.CMP_REMOVE_ASSET) {
			return sub(Liferay.Language.get('x-removed-the-asset-x'), [
				<strong key="creatorName">{auditEvent.creator?.name}</strong>,
				<strong key="changedField">
					{auditEvent.auditFieldChanges?.[0].name}
				</strong>,
			]);
		}

		return sub(Liferay.Language.get('x-updated-the-x'), [
			<strong key="creatorName">{auditEvent.creator?.name}</strong>,
			<strong key="changedFields">
				{joinWithAnd(
					auditEvent.auditFieldChanges?.map(
						(auditFieldChange) =>
							FIELD_NAME[auditFieldChange.name] ??
							auditFieldChange.name
					) || []
				)}
			</strong>,
		]);
	};

	/**
	 * The `oldValue` and `newValue` can be in different formats according to
	 * the field's `name` property. For example:
	 * {
	 * 	name: "state",
	 *  newValue: {key: "inProgress", name: "In Progress"},
	 * 	oldValue: {key: "notStarted", name: "Not Started"},
	 * }
	 *
	 * {
	 * 	name: "title",
	 *  newValue: "New Title",
	 * 	oldValue: "Old Title",
	 * }
	 */
	const getAuditFieldChangeValueLabel = (value: any): string => {
		if (typeof value === 'string') {
			return value ?? Liferay.Language.get('none');
		}

		if (typeof value === 'object') {
			return value?.name ?? Liferay.Language.get('none');
		}

		return Liferay.Language.get('none');
	};

	return (
		<List.Item className="border-0" flex>
			<List.ItemField>
				<AssigneeAvatar
					image={auditEvent.creator?.image}
					name={auditEvent.creator?.name || ''}
				/>
			</List.ItemField>

			<List.ItemField expand>
				<List.ItemTitle className="text-weight-normal">
					{getAuditEventLabel(auditEvent)}
				</List.ItemTitle>

				<List.ItemText>
					{new Date(auditEvent.dateCreated).toLocaleString()}
				</List.ItemText>

				{showDetails && (
					<div>
						{auditEvent.auditFieldChanges?.map(
							(auditFieldChange, index) => (
								<span className="c-mt-2" key={index}>
									<strong>
										{FIELD_NAME[auditFieldChange.name]}:
									</strong>

									<div className="autofit-padded-no-gutters-x autofit-row autofit-row-center">
										<div className="autofit-col text-secondary">
											{getAuditFieldChangeValueLabel(
												auditFieldChange.oldValue
											)}
										</div>

										<div className="autofit-col text-secondary">
											<div>
												<ClayIcon symbol="order-arrow-right" />
											</div>
										</div>

										<div className="autofit-col autofit-col-expand">
											{getAuditFieldChangeValueLabel(
												auditFieldChange.newValue
											)}
										</div>
									</div>
								</span>
							)
						)}
					</div>
				)}

				<div className="c-mt-2">
					<ClayButton
						borderless
						className="c-px-2 c-py-1 text-2"
						displayType="secondary"
						onClick={() => setShowDetails(!showDetails)}
					>
						{showDetails
							? Liferay.Language.get('hide-details')
							: Liferay.Language.get('view-details')}
					</ClayButton>
				</div>
			</List.ItemField>
		</List.Item>
	);
}

export default function TaskHistory({apiURL}: {apiURL: string}) {
	const [auditEvents, setAuditEvents] = useState<AuditEvent[]>([]);
	const [initialDataLoaded, setInitialDataLoaded] = useState(false);
	const [loading, setLoading] = useState(true);

	const containerRef = useRef<HTMLDivElement>(null);

	const fetchAuditEvents = useCallback(async () => {
		setLoading(true);

		try {
			const response = await fetch(apiURL, {
				method: 'GET',
			});
			const data = (await response.json()) as Data;

			setAuditEvents(data.auditEvents);
		}
		finally {
			setLoading(false);
		}
	}, [apiURL]);

	/**
	 * This effect lazily loads the task's audit history. It uses an
	 * `IntersectionObserver` to fetch data only when the component first
	 * becomes visible in the viewport (for example, when the user switches to
	 * the 'History' tab).
	 */
	useEffect(() => {
		if (!containerRef.current) {
			return;
		}

		const observer = new IntersectionObserver(
			([entry], observer) => {
				if (entry.isIntersecting) {
					fetchAuditEvents();

					setInitialDataLoaded(true);

					observer.disconnect();
				}
			},
			{threshold: 0.01}
		);

		observer.observe(containerRef.current);

		return () => observer.disconnect();
	}, [fetchAuditEvents]);

	useEffect(() => {
		const handleFDSDisplayUpdated = ({id}: {id: string}) => {
			if (initialDataLoaded && id === RELATED_ASSETS_SECTION) {
				fetchAuditEvents();
			}
		};

		const handleUpdateTaskHistory = () => {
			if (initialDataLoaded) {
				fetchAuditEvents();
			}
		};

		Liferay.on(FDS_EVENT.DISPLAY_UPDATED, handleFDSDisplayUpdated);
		Liferay.on(UPDATE_TASK_HISTORY, handleUpdateTaskHistory);

		return () => {
			Liferay.detach(FDS_EVENT.DISPLAY_UPDATED, handleFDSDisplayUpdated);
			Liferay.detach(UPDATE_TASK_HISTORY, handleUpdateTaskHistory);
		};
	}, [fetchAuditEvents, initialDataLoaded]);

	return (
		<div className="task-history-container" ref={containerRef}>
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				<List>
					{auditEvents
						.filter(({auditFieldChanges, eventType}) => {
							return (
								eventType !== EventType.UPDATE ||
								!!auditFieldChanges?.length
							);
						})
						.map((auditEvent, index) => (
							<TaskHistoryItem
								auditEvent={auditEvent}
								key={index}
							/>
						))}
				</List>
			)}
		</div>
	);
}
