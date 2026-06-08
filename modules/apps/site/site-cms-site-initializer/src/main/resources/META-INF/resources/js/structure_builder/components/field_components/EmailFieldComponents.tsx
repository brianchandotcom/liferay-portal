/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import ClayPanel from '@clayui/panel';
import {useId} from 'frontend-js-components-web';
import React from 'react';

import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectPublishedChildren from '../../selectors/selectPublishedChildren';
import {EmailField, Field} from '../../utils/field';

type DomainItem = {
	label: string;
	value: string;
};

export default function getEmailFieldComponents(): {
	AdvancedTabComponent?: React.FC<{disabled?: boolean; field: Field}>;
	FirstSectionComponent?: React.FC<{disabled?: boolean; field: Field}>;
	SecondSectionComponent?: React.FC<{disabled?: boolean; field: Field}>;
} {
	return {
		AdvancedTabComponent,
		SecondSectionComponent,
	};
}

function SecondSectionComponent({
	disabled,
	field,
}: {
	disabled?: boolean;
	field: Field;
}) {
	const emailField = field as EmailField;

	const dispatch = useStateDispatch();
	const publishedChildren = useSelector(selectPublishedChildren);

	const isPublished = publishedChildren.has(field.uuid);

	return (
		<ClayForm.Group className="mb-3">
			<ClayCheckbox
				checked={emailField.settings.uniqueValues || false}
				disabled={disabled || isPublished}
				label={Liferay.Language.get('accept-unique-values-only')}
				onChange={(event) => {
					dispatch({
						settings: {
							...emailField.settings,
							uniqueValues: event.target.checked,
						},
						type: 'update-field',
						uuid: field.uuid,
					});
				}}
			/>
		</ClayForm.Group>
	);
}

function AdvancedTabComponent({
	disabled,
	field,
}: {
	disabled?: boolean;
	field: Field;
}) {
	const emailField = field as EmailField;

	const dispatch = useStateDispatch();
	const publishedChildren = useSelector(selectPublishedChildren);

	const isPublished = publishedChildren.has(field.uuid);

	const blockedDomainsId = useId();
	const domainsId = useId();

	const updateSettings = (settings: Partial<EmailField['settings']>) => {
		dispatch({
			settings: {
				...emailField.settings,
				...settings,
			},
			type: 'update-field',
			uuid: field.uuid,
		});
	};

	return (
		<>
			<ClayPanel
				className="mt-4"
				collapsable
				defaultExpanded
				displayTitle={
					<ClayPanel.Title className="panel-title text-secondary">
						{Liferay.Language.get('blocked-domains')}
					</ClayPanel.Title>
				}
				displayType="unstyled"
				showCollapseIcon
			>
				<ClayPanel.Body className="pt-4">
					<ClayForm.Group className="mb-0">
						<label htmlFor={blockedDomainsId}>
							{Liferay.Language.get('blocked-domains')}
						</label>

						<ClayMultiSelect
							aria-label={Liferay.Language.get('blocked-domains')}
							disabled={disabled || isPublished}
							id={blockedDomainsId}
							items={toItems(emailField.settings.blockedDomains)}
							onItemsChange={(items: DomainItem[]) => {
								updateSettings({
									blockedDomains: toDomains(items),
								});
							}}
						/>
					</ClayForm.Group>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={
					<ClayPanel.Title className="panel-title text-secondary">
						{Liferay.Language.get('autocomplete')}
					</ClayPanel.Title>
				}
				displayType="unstyled"
				showCollapseIcon
			>
				<ClayPanel.Body className="pt-4">
					<ClayForm.Group className="mb-0">
						<label htmlFor={domainsId}>
							{Liferay.Language.get('domains')}
						</label>

						<ClayMultiSelect
							aria-label={Liferay.Language.get('domains')}
							disabled={disabled || isPublished}
							id={domainsId}
							items={toItems(
								emailField.settings.autocompleteDomains
							)}
							onItemsChange={(items: DomainItem[]) => {
								updateSettings({
									autocompleteDomains: toDomains(items),
									autocompleteEnabled: Boolean(items.length),
								});
							}}
						/>
					</ClayForm.Group>
				</ClayPanel.Body>
			</ClayPanel>
		</>
	);
}

function toItems(value?: string): DomainItem[] {
	if (!value) {
		return [];
	}

	return value.split(',').map((domain) => ({label: domain, value: domain}));
}

function toDomains(items: DomainItem[]): string | undefined {
	return (
		items.map((item) => normalizeDomain(item.value)).join(',') || undefined
	);
}

function normalizeDomain(value: string): string {
	const domain = value.trim();

	if (!domain || domain.startsWith('@')) {
		return domain;
	}

	return `@${domain}`;
}
