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

import sxpElementSchema from '../../schemas/sxp-query-element.schema.json';
import {DEFAULT_SXP_ELEMENT_ICON} from '../utils/data';
import {getLocalizedText} from '../utils/language';
import ThemeContext from './ThemeContext';
import JSONInput from './sxp_element/JSONInput';

function JSONSXPElement({
	collapseAll,
	sxpElement,
	error = {},
	id,
	index,
	isSubmitting,
	onDeleteSXPElement,
	prefixedId,
	readOnly,
	setFieldTouched = () => {},
	setFieldValue = () => {},
	touched = {},
	uiConfigurationValues = {},
}) {
	const {locale} = useContext(ThemeContext);

	const [active, setActive] = useState(false);
	const [collapse, setCollapse] = useState(collapseAll);

	const description = getLocalizedText(sxpElement.description_i18n, locale);
	const title = getLocalizedText(sxpElement.title_i18n, locale);

	useEffect(() => {
		setCollapse(collapseAll);
	}, [collapseAll]);

	const _inputName = () =>
		`elementInstances[${index}].uiConfigurationValues.sxpElement`;

	const _hasError = () =>
		touched.uiConfigurationValues &&
		touched.uiConfigurationValues.sxpElement &&
		error.uiConfigurationValues &&
		!!error.uiConfigurationValues.sxpElement;

	return (
		<div className="sheet sxp-element" id={prefixedId}>
			<ClayList className="configuration-header-list">
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker size="md">
							<ClayIcon
								symbol={
									sxpElement.elementDefinition?.icon ||
									DEFAULT_SXP_ELEMENT_ICON
								}
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						{title && (
							<ClayList.ItemTitle>{title}</ClayList.ItemTitle>
						)}

						{description && (
							<ClayList.ItemText subtext={true}>
								{description}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					{onDeleteSXPElement && (
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
									onClick={() => onDeleteSXPElement(id)}
								>
									{Liferay.Language.get('remove')}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					)}

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
						autocompleteSchema={sxpElementSchema}
						disabled={isSubmitting}
						name={_inputName(index)}
						readOnly={readOnly}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={
							uiConfigurationValues.sxpElement ||
							JSON.stringify(sxpElement, null, '\t')
						}
					/>

					{_hasError() && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{error.uiConfigurationValues.sxpElement}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</div>
			)}
		</div>
	);
}

JSONSXPElement.propTypes = {
	collapseAll: PropTypes.bool,
	error: PropTypes.object,
	id: PropTypes.number,
	index: PropTypes.number,
	isSubmitting: PropTypes.bool,
	onDeleteSXPElement: PropTypes.func,
	prefixedId: PropTypes.string,
	readOnly: PropTypes.bool,
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	sxpElement: PropTypes.object,
	touched: PropTypes.object,
	uiConfigurationValues: PropTypes.object,
};

export default React.memo(JSONSXPElement);
