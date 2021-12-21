/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayToolbar from '@clayui/toolbar';
import {TranslationAdminSelector} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useRef} from 'react';

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {defaultLanguageId} from '../../../constants';
import XMLUtil from '../../../source-builder/xmlUtil';
import {getAvailableLocalesObject} from '../../../util/availableLocales';

export default function UpperToolbar({
	displayNames,
	languageIds,
	translations,
	version,
}) {
	const {
		currentEditor,
		definitionTitle,
		selectedLanguageId,
		setDefinitionTitle,
		setDeserialize,
		setSelectedLanguageId,
		setShowInvalidContentError,
		setSourceView,
		sourceView,
	} = useContext(DefinitionBuilderContext);
	const inputRef = useRef(null);

	const availableLocales = getAvailableLocalesObject(
		displayNames,
		languageIds
	);

	const onSelectedLanguageIdChange = (id) => {
		if (id) {
			setSelectedLanguageId(id);
		}
	};

	const onInputBlur = () => {
		if (definitionTitle && selectedLanguageId) {
			translations[selectedLanguageId] = definitionTitle;
		}
	};

	useEffect(() => {
		if (selectedLanguageId) {
			setDefinitionTitle(translations[selectedLanguageId]);
		}
	}, [selectedLanguageId, setDefinitionTitle, translations]);

	return (
		<ClayToolbar className="upper-toolbar">
			<ClayLayout.ContainerFluid>
				<ClayToolbar.Nav>
					<ClayToolbar.Item>
						<TranslationAdminSelector
							activeLanguageIds={languageIds}
							adminMode
							availableLocales={availableLocales}
							defaultLanguageId={defaultLanguageId}
							onSelectedLanguageIdChange={
								onSelectedLanguageIdChange
							}
							translations={translations}
						/>
					</ClayToolbar.Item>

					<ClayToolbar.Item expand>
						<ClayInput
							className="form-control-inline"
							id="definition-title"
							onBlur={() => onInputBlur()}
							onChange={({target: {value}}) => {
								setDefinitionTitle(value);
							}}
							placeholder={Liferay.Language.get(
								'untitled-workflow'
							)}
							ref={inputRef}
							type="text"
							value={definitionTitle || ''}
						/>
					</ClayToolbar.Item>

					{version !== '0' && (
						<ClayToolbar.Item>
							<ClayLabel
								className="version"
								displayType="secondary"
							>
								<div>
									{`${Liferay.Language.get('version')}:`}

									<span className="version-text">
										{version}
									</span>
								</div>
							</ClayLabel>
						</ClayToolbar.Item>
					)}

					<ClayToolbar.Item>
						<ClayButton
							displayType="secondary"
							onClick={() => {
								window.history.back();
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayButton displayType="secondary">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayButton displayType="primary">
							{Liferay.Language.get('publish')}
						</ClayButton>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						{sourceView ? (
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => {
									if (
										XMLUtil.validateDefinition(
											currentEditor.getData()
										)
									) {
										setSourceView(false);
										setDeserialize(true);
									}
									else {
										setShowInvalidContentError(true);
									}
								}}
								symbol="rules"
								title={Liferay.Language.get('diagram-view')}
							/>
						) : (
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => setSourceView(true)}
								symbol="code"
								title={Liferay.Language.get('source-view')}
							/>
						)}
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayLayout.ContainerFluid>
		</ClayToolbar>
	);
}

UpperToolbar.propTypes = {
	displayNames: PropTypes.arrayOf(PropTypes.string).isRequired,
	languageIds: PropTypes.arrayOf(PropTypes.string).isRequired,
	title: PropTypes.PropTypes.string.isRequired,
	translations: PropTypes.object,
	version: PropTypes.PropTypes.string.isRequired,
};
