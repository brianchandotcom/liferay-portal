/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import List from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {fetch} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useImperativeHandle,
	useRef,
	useState,
} from 'react';

export const UPDATE_HISTORY = 'cmp-update-history';

export enum EventType {
	ADD = 'ADD',
	UPDATE = 'UPDATE',
}

type AuditFieldChange = {
	name: string;
	newValue: any;
	oldValue: any;
};

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

export type AuditEvent<T> = {
	auditFieldChanges?: AuditFieldChange[];
	creator: Creator;
	dateCreated: string;
	eventType: T | EventType;
};

type Data<T> = {
	auditEvents: AuditEvent<T>[];
};

type FieldType =
	| 'assignee'
	| 'date'
	| 'picklist'
	| 'relationship'
	| 'text'
	| 'user';

export type Fields = Record<string, {label: string; type: FieldType}>;

export interface HistoryHandle {
	refresh: () => void;
}

export function joinWithAnd(items: string[]) {
	if (!items?.length) {
		return '';
	}

	return new Intl.ListFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		style: 'long',
		type: 'conjunction',
	}).format(items);
}

function UserValueLabel({userId}: {userId: number}) {
	const [loading, setLoading] = useState(false);
	const [userName, setUserName] = useState<string | null>(null);

	useEffect(() => {
		if (userId) {
			setLoading(true);

			fetch(`/o/headless-admin-user/v1.0/user-accounts/${userId}`)
				.then((response) => response.json())
				.then((responseData) => {
					setUserName(responseData.name);
				})
				.catch(() => {
					setUserName(Liferay.Language.get('none'));
				})
				.finally(() => {
					setLoading(false);
				});
		}
	}, [userId]);

	if (loading) {
		return <ClayLoadingIndicator className="m-0" size="xs" />;
	}

	if (!userId) {
		return <>{Liferay.Language.get('none')}</>;
	}

	return <>{userName || Liferay.Language.get('none')}</>;
}

function HistoryItemDetails({
	auditFieldChanges,
	fields,
}: {
	auditFieldChanges?: AuditFieldChange[];
	fields: Fields;
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
	const getAuditFieldChangeValueLabel = (fieldName: string, value: any) => {
		const {type} = fields[fieldName];

		if ((type === 'assignee' || type === 'picklist') && value?.name) {
			return value?.name;
		}
		if (type === 'date' && value) {
			const date = new Date(value);

			return !isNaN(date.getTime())
				? date.toLocaleDateString()
				: Liferay.Language.get('none');
		}
		else if (type === 'relationship' && value?.titleValue) {
			return value?.titleValue;
		}
		else if (type === 'text' && value) {
			return value;
		}
		else if (type === 'user') {
			return <UserValueLabel userId={value} />;
		}

		return Liferay.Language.get('none');
	};

	return (
		<div>
			{auditFieldChanges
				?.filter(({name}) => fields[name])
				.map((auditFieldChange, index) => (
					<div className="c-mt-3" key={index}>
						<strong>{fields[auditFieldChange.name].label}:</strong>

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

function HistoryItem<T>({
	auditEvent,
	fields,
	getAuditEventLabel,
}: {
	auditEvent: AuditEvent<T>;
	fields: Fields;
	getAuditEventLabel: (auditEvent: AuditEvent<T>) => React.ReactNode;
}) {
	const [showDetails, setShowDetails] = useState(false);

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
					<HistoryItemDetails
						auditFieldChanges={auditEvent.auditFieldChanges}
						fields={fields}
					/>
				)}

				{(auditEvent.eventType === EventType.ADD ||
					auditEvent.eventType === EventType.UPDATE) && (
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
				)}
			</List.ItemField>
		</List.Item>
	);
}

export default function History<T>({
	apiURL,
	fields,
	getAuditEventLabel,
	innerRef,
}: {
	apiURL: string;
	fields: Fields;
	getAuditEventLabel: (auditEvent: AuditEvent<T>) => React.ReactNode;
	innerRef?: React.RefObject<HistoryHandle>;
}) {
	const [auditEvents, setAuditEvents] = useState<AuditEvent<T>[]>([]);
	const [initialDataLoaded, setInitialDataLoaded] = useState(false);
	const [loading, setLoading] = useState(true);

	const containerRef = useRef<HTMLDivElement>(null);

	const fetchAuditEvents = useCallback(async () => {
		setLoading(true);

		try {
			const response = await fetch(apiURL, {
				method: 'GET',
			});
			const data = (await response.json()) as Data<T>;

			setAuditEvents(data.auditEvents);
		}
		finally {
			setLoading(false);
		}
	}, [apiURL]);

	/**
	 * This effect lazily loads the audit history. It uses an
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
		const handleUpdateHistory = () => {
			if (initialDataLoaded) {
				fetchAuditEvents();
			}
		};

		Liferay.on(UPDATE_HISTORY, handleUpdateHistory);

		return () => {
			Liferay.detach(UPDATE_HISTORY, handleUpdateHistory);
		};
	}, [fetchAuditEvents, initialDataLoaded]);

	useImperativeHandle(innerRef, () => ({
		refresh() {
			if (initialDataLoaded) {
				fetchAuditEvents();
			}
		},
	}));

	return (
		<div className="history-container" ref={containerRef}>
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
							<HistoryItem
								auditEvent={auditEvent}
								fields={fields}
								getAuditEventLabel={getAuditEventLabel}
								key={index}
							/>
						))}
				</List>
			)}
		</div>
	);
}
