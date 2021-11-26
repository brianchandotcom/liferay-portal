/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import getCN from 'classnames';
import React, {useState} from 'react';

const ERROR_OMIT_KEYS = [
	'className',
	'sxpElementId',
	'localizedMessage',
	'msg',
	'throwable',
	'severity',
];

const CONFIGURATION_FIELD_NAME = {
	advanced_configuration: Liferay.Language.get('advanced-configuration'),
	aggregation_configuration: Liferay.Language.get(
		'aggregation-configuration'
	),
	highlight_configuration: Liferay.Language.get('highlight-configuration'),
	parameter_configuration: Liferay.Language.get('parameter-configuration'),
	query_configuration: Liferay.Language.get('query-configuration'),
	sort_configuration: Liferay.Language.get('sort-configuration'),
	sxpElementTemplateJSON: Liferay.Language.get('element-template'),
	uiConfigurationJSON: Liferay.Language.get('ui-configuration'),
};

// Types from search-experiences-blueprints-api/src/main/java/com/liferay/search/experiences/blueprints/message/Severity.java

const SEVERITY_DISPLAY_TYPE = {
	ERROR: 'danger',
	INFO: 'info',
	WARN: 'warning',
};

const getConfigurationFieldName = (rootProperty) => {
	const configName = Object.keys(CONFIGURATION_FIELD_NAME).find((key) =>
		rootProperty?.includes(key)
	);

	return configName ? CONFIGURATION_FIELD_NAME[configName] : '';
};

const prettyPrint = (value) => {
	return JSON.stringify(value, null, 2);
};

function ErrorListItem({item, onFocusSXPElement}) {
	const [collapse, setCollapse] = useState(true);

	const _getDescription = () => {
		const configurationFieldName = getConfigurationFieldName(
			item.rootProperty
		);

		return configurationFieldName
			? `${item.msg} (${configurationFieldName})`
			: item.msg;
	};

	const _handleCollapse = () => {
		setCollapse(!collapse);
	};

	const _handleFocusSXPElement = () => {
		onFocusSXPElement(item.sxpElementId);
	};

	const _isCollapsible = () => {
		return Object.keys(item).some(
			(property) => !ERROR_OMIT_KEYS.includes(property)
		);
	};

	return (
		<ClayAlert
			className={getCN('error-list-item', {
				collapsible: _isCollapsible(),
			})}
			displayType={SEVERITY_DISPLAY_TYPE[item.severity] || 'danger'}
		>
			<span className="message" onClick={_handleCollapse}>
				<span className="title">
					{item.localizedMessage || Liferay.Language.get('error')}
				</span>

				{item.msg && (
					<span className="description">{_getDescription()}</span>
				)}
			</span>

			{!!item.sxpElementId && (
				<div className="scroll-button">
					<ClayButton alert onClick={_handleFocusSXPElement} small>
						{Liferay.Language.get('view-element')}
					</ClayButton>
				</div>
			)}

			{_isCollapsible() && (
				<ClayButtonWithIcon
					borderless
					className="collapse-button text-danger"
					displayType="unstyled"
					onClick={_handleCollapse}
					small
					symbol={collapse ? 'angle-right' : 'angle-down'}
				/>
			)}

			{!collapse && _isCollapsible() && (
				<ClayAlert.Footer>
					{Object.keys(item).map(
						(property) =>
							!ERROR_OMIT_KEYS.includes(property) && (
								<ClayLayout.Row justify="start" key={property}>
									<ClayLayout.Col
										className="property"
										size={3}
									>
										{property}
									</ClayLayout.Col>

									<ClayLayout.Col size={9}>
										<code>
											{typeof item[property] === 'object'
												? prettyPrint(item[property])
												: item[property]}
										</code>
									</ClayLayout.Col>
								</ClayLayout.Row>
							)
					)}
				</ClayAlert.Footer>
			)}
		</ClayAlert>
	);
}

export default ErrorListItem;
