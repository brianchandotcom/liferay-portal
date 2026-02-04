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
	name: FieldName;
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

const FIELDS = {
	assignTo: {label: Liferay.Language.get('assignee')},
	description: {label: Liferay.Language.get('description')},
	dueDate: {label: Liferay.Language.get('due-date')},
	r_cmpProjectToCMPTasks_c_cmpProjectId: {
		label: Liferay.Language.get('project'),
	},
	state: {label: Liferay.Language.get('state')},
	title: {label: Liferay.Language.get('title')},
} as const;

type FieldName = keyof typeof FIELDS;

function joinWithAnd(items: string[]) {
	if (!items?.length) {
		return '';
	}

	return new Intl.ListFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		style: 'long',
		type: 'conjunction',
	}).format(items);
}

function AssigneeValueLabel({
	value,
}: {
	value: {
		classNameId: number;
		classPK: number;
	};
}) {
	const [assigneeName, setAssigneeName] = useState<string | null>(null);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (value?.classPK) {
			setLoading(true);

			fetch(`/o/headless-admin-user/v1.0/user-accounts/${value.classPK}`)
				.then((response) => response.json())
				.then((responseData) => {
					setAssigneeName(responseData.name);
				})
				.catch(() => {
					setAssigneeName(Liferay.Language.get('none'));
				})
				.finally(() => {
					setLoading(false);
				});
		}
	}, [value]);

	if (loading) {
		return <ClayLoadingIndicator className="m-0" size="xs" />;
	}

	if (!value) {
		return Liferay.Language.get('none');
	}

	return assigneeName || Liferay.Language.get('none');
}

function TaskHistoryItemDetails({
	auditFieldChanges,
}: {
	auditFieldChanges?: AuditFieldChange[];
}) {

	/**
	 * The `oldValue` and `newValue` can be in different formats according to
	 * the field's `name` property. For example:
	 *
	 * {
	 * 	name: "state",
	 * 	newValue: {key: "inProgress", name: "In Progress"},
	 * 	oldValue: {key: "notStarted", name: "Not Started"},
	 * }
	 *
	 * {
	 * 	name: "title",
	 * 	newValue: "New Title",
	 * 	oldValue: "Old Title",
	 * }
	 *
	 * {
	 * 	"name": "r_cmpProjectToCMPTasks_c_cmpProjectId",
	 * 	"newValue": { "objectEntryId": 37141, "titleValue": "Project 1"}
	 * }
	 */
	const getAuditFieldChangeValueLabel = (
		fieldName: FieldName,
		value: any
	) => {
		if (fieldName === 'assignTo') {
			return <AssigneeValueLabel value={value} />;
		}

		if (fieldName === 'dueDate' && value) {
			const date = new Date(value);

			return !isNaN(date.getTime())
				? date.toLocaleDateString()
				: Liferay.Language.get('none');
		}

		if (
			fieldName === 'r_cmpProjectToCMPTasks_c_cmpProjectId' &&
			value?.titleValue
		) {
			return value?.titleValue;
		}

		if (typeof value === 'string' && value) {
			return value;
		}

		if (typeof value === 'object' && value?.name) {
			return value?.name;
		}

		return Liferay.Language.get('none');
	};

	return (
		<div>
			{auditFieldChanges?.map((auditFieldChange, index) => (
				<div className="c-mt-3" key={index}>
					<strong>{FIELDS[auditFieldChange.name].label}:</strong>

					<div className="autofit-padded-no-gutters-x autofit-row autofit-row-center">
						<div className="autofit-col text-secondary">
							{getAuditFieldChangeValueLabel(
								auditFieldChange.name,
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
								auditFieldChange.name,
								auditFieldChange.newValue
							)}
						</div>
					</div>
				</div>
			))}
		</div>
	);
}

function TaskHistoryItem({auditEvent}: {auditEvent: AuditEvent}) {
	const [showDetails, setShowDetails] = useState(false);

	/**
	 * Gets the label like: "Test Test updated the Task."
	 */
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
						({name}) => FIELDS[name].label ?? name
					) || []
				)}
			</strong>,
		]);
	};

	return (
		<List.Item className="border-0 c-mb-2 c-py-2" flex>
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
					<TaskHistoryItemDetails
						auditFieldChanges={auditEvent.auditFieldChanges}
					/>
				)}

				<div className="c-mt-2">
					<ClayButton
						borderless
						className="btn-xs"
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
