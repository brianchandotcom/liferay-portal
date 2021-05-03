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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import JSONInput from '../shared/element/JSONInput';
import ThemeContext from './ThemeContext';

function JSONElement({
	collapseAll,
	elementTemplateJSON,
	error = {},
	id,
	index,
	onDeleteElement,
	prefixedId,
	setFieldTouched,
	setFieldValue,
	touched = {},
	uiConfigurationValues = {},
}) {
	const {locale} = useContext(ThemeContext);

	const [active, setActive] = useState(false);
	const [collapse, setCollapse] = useState(collapseAll);

	useEffect(() => {
		setCollapse(collapseAll);
	}, [collapseAll]);

	const _inputName = () =>
		`selectedQueryElements[${index}].uiConfigurationValues.elementTemplateJSON`;

	const _hasError = () =>
		touched.uiConfigurationValues &&
		touched.uiConfigurationValues.elementTemplateJSON &&
		error.uiConfigurationValues &&
		!!error.uiConfigurationValues.elementTemplateJSON;

	return (
		<div className="element sheet" id={prefixedId}>
			<ClayList className="configuration-header-list">
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker size="md">
							<ClayIcon symbol={elementTemplateJSON.icon} />
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						{elementTemplateJSON.title && (
							<ClayList.ItemTitle>
								{elementTemplateJSON.title[locale] ||
									elementTemplateJSON.title}
							</ClayList.ItemTitle>
						)}

						{elementTemplateJSON.description && (
							<ClayList.ItemText subtext={true}>
								{elementTemplateJSON.description[locale] ||
									elementTemplateJSON.description}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					<ClayDropDown
						active={active}
						alignmentPosition={3}
						onActiveChange={setActive}
						trigger={
							<ClayList.ItemField>
								<ClayButton
									aria-label={Liferay.Language.get(
										'dropdown'
									)}
									className="component-action"
									displayType="unstyled"
								>
									<ClayIcon symbol="ellipsis-v" />
								</ClayButton>
							</ClayList.ItemField>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								onClick={() => onDeleteElement(id)}
							>
								{Liferay.Language.get('remove')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>

					<ClayList.ItemField>
						<ClayButton
							aria-label={
								!collapse
									? Liferay.Language.get('collapse')
									: Liferay.Language.get('expand')
							}
							className="component-action"
							displayType="unstyled"
							onClick={() => {
								setCollapse(!collapse);
							}}
						>
							<ClayIcon
								symbol={
									!collapse ? 'angle-down' : 'angle-right'
								}
							/>
						</ClayButton>
					</ClayList.ItemField>
				</ClayList.Item>
			</ClayList>

			{!collapse && (
				<div
					className={getCN('json-configuration-editor', {
						'has-error': _hasError(),
					})}
				>
					<JSONInput
						name={_inputName(index)}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={
							uiConfigurationValues.elementTemplateJSON ||
							JSON.stringify(elementTemplateJSON, null, '\t')
						}
					/>

					{_hasError() && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />
								{
									error.uiConfigurationValues
										.elementTemplateJSON
								}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</div>
			)}
		</div>
	);
}

JSONElement.propTypes = {
	collapseAll: PropTypes.bool,
	elementTemplateJSON: PropTypes.object,
	error: PropTypes.object,
	id: PropTypes.number,
	index: PropTypes.number,
	onDeleteElement: PropTypes.func,
	prefixedId: PropTypes.string,
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	touched: PropTypes.object,
	uiConfigurationValues: PropTypes.object,
};

export default React.memo(JSONElement);
