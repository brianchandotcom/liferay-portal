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
import React, {useContext, useEffect, useMemo, useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';
import ThemeContext from './ThemeContext';

function JSONElement({
	collapseAll,
	elementTemplateJSON,
	id,
	onDeleteElement,
	onUpdateElement,
}) {
	const {locale} = useContext(ThemeContext);

	const [active, setActive] = useState(false);
	const [collapse, setCollapse] = useState(collapseAll);
	const [hasError, setHasError] = useState(false);

	useEffect(() => {
		setCollapse(collapseAll);
	}, [collapseAll]);

	function handleChange(value) {
		try {
			const parseJSON = JSON.parse(value);

			onUpdateElement(id, {
				elementOutput: parseJSON,
				elementTemplateJSON: parseJSON,
			});

			setHasError(false);
		}
		catch {
			setHasError(true);
		}
	}

	return (
		<div className="configuration-element-sheet sheet">
			{useMemo(() => {
				return (
					<ClayList className="configuration-header-list">
						<ClayList.Item flex>
							<ClayList.ItemField>
								<ClaySticker size="md">
									<ClayIcon
										symbol={elementTemplateJSON.icon}
									/>
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
										{elementTemplateJSON.description[
											locale
										] || elementTemplateJSON.description}
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
											!collapse
												? 'angle-down'
												: 'angle-right'
										}
									/>
								</ClayButton>
							</ClayList.ItemField>
						</ClayList.Item>
					</ClayList>
				);
			}, [
				active,
				collapse,
				onDeleteElement,
				id,
				elementTemplateJSON,
				locale,
			])}

			{!collapse && (
				<div
					className={getCN('json-configuration-editor', {
						'has-error': hasError,
					})}
				>
					<label>{Liferay.Language.get('json')}</label>

					<CodeMirrorEditor
						onChange={handleChange}
						value={JSON.stringify(elementTemplateJSON, null, '\t')}
					/>

					{hasError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />
								{Liferay.Language.get(
									'unable-to-apply-changes-due-to-invalid-json'
								)}
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
	id: PropTypes.number,
	onDeleteElement: PropTypes.func,
	onUpdateElement: PropTypes.func,
};

export default React.memo(JSONElement);
