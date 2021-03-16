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
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import ClayToolbar from '@clayui/toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import CodeMirrorEditor from '../shared/CodeMirrorEditor';
import ErrorBoundary from '../shared/ErrorBoundary';
import PreviewModal from '../shared/PreviewModal';
import SearchInput from '../shared/SearchInput';
import ThemeContext from '../shared/ThemeContext';
import Element from '../shared/element/index';
import {
	getUIConfigurationValues,
	isNotEmpty,
	openErrorToast,
	renameKeys,
	sub,
} from '../utils/utils';

function EditElementForm({
	blueprintId,
	blueprintType,
	initialConfigurationString,
	initialDescription,
	initialTitle,
	predefinedVariables,
	redirectURL,
	submitFormURL,
}) {
	const {defaultLocale, namespace} = useContext(ThemeContext);

	const initialConfiguration = JSON.parse(initialConfigurationString);

	const form = useRef();
	const elementTemplateJSONRef = useRef();

	const [isSubmitting, setIsSubmitting] = useState(false);
	const [expand, setExpand] = useState(false);

	const [variables, setVariables] = useState(predefinedVariables);

	initialConfiguration.elementTemplateJSON.title = renameKeys(
		initialTitle,
		(str) => str.replace('-', '_')
	);
	initialConfiguration.elementTemplateJSON.description = renameKeys(
		initialDescription,
		(str) => str.replace('-', '_')
	);

	const [elementTemplateJSON, setElementTemplateJSON] = useState(
		JSON.stringify(initialConfiguration.elementTemplateJSON, null, '\t')
	);
	const [uiConfigurationJSON, setUIConfigurationJSON] = useState(
		JSON.stringify(initialConfiguration.uiConfigurationJSON, null, '\t')
	);

	useEffect(() => {

		// Workaround to force a re-render so `elementTemplateJSONRef` will be
		// defined when calling `_onVariableClick`

		setExpand(true);
	}, []);

	function _appendEntryLocale(entry, name, formData) {
		if (typeof entry == 'object') {
			return Object.keys(entry).map((key) => {
				formData.append(
					`${namespace}${name}_${key.replace('-', '_')}`,
					entry[key]
				);
			});
		}
		else if (typeof entry == 'string') {
			formData.append(
				`${namespace}${name}_${defaultLocale.replace('-', '_')}`,
				entry
			);
		}
	}

	const _handleSearchFilter = useCallback(
		(value) => {
			const filteredParameterDefinitions = predefinedVariables.map(
				(category) => ({
					...category,
					parameterDefinitions: category.parameterDefinitions.filter(
						({description}) =>
							description
								.toLowerCase()
								.includes(value.toLowerCase())
					),
				})
			);

			const filteredCategories = filteredParameterDefinitions.filter(
				({parameterDefinitions}) => parameterDefinitions.length
			);

			setVariables(filteredCategories);
		},
		[predefinedVariables]
	);

	function _onVariableClick(variable) {
		const doc = elementTemplateJSONRef.current.getDoc();
		const cursor = doc.getCursor();

		doc.replaceRange(variable, cursor);
	}

	function _renderPreviewBody() {
		let previewElementTemplateJSON = {};
		let previewUIConfigurationJSON = {};

		try {
			previewElementTemplateJSON = JSON.parse(elementTemplateJSON);
			previewUIConfigurationJSON = JSON.parse(uiConfigurationJSON);
		}
		catch (e) {
			return (
				<ClayEmptyState
					description={Liferay.Language.get(
						'json-may-be-incorrect-and-we-were-unable-to-load-a-preview-of-the-configuration'
					)}
					imgSrc="/o/admin-theme/images/states/empty_state.gif"
					title={Liferay.Language.get('unable-to-load-preview')}
				/>
			);
		}

		return (
			<div className="portlet-blueprints-admin">
				<ErrorBoundary>
					<Element
						collapseAll={false}
						elementTemplateJSON={previewElementTemplateJSON}
						uiConfigurationJSON={previewUIConfigurationJSON}
						uiConfigurationValues={getUIConfigurationValues(
							previewUIConfigurationJSON
						)}
					/>
				</ErrorBoundary>
			</div>
		);
	}

	const _validateConfigKeys = (
		elementTemplateJSON,
		parseUIConfigurationJSON
	) => {
		const elementKeys = [
			...elementTemplateJSON.matchAll(/\$\{config.\w+\}/g),
		].map((item) => item[0].replace('${config.', '').replace('}', ''));

		const uiConfigKeys = parseUIConfigurationJSON.map((item) => item.key);

		const missingKeys = elementKeys.filter(
			(item) => !uiConfigKeys.includes(item)
		);

		if (missingKeys.length > 0) {
			throw sub(
				Liferay.Language.get(
					'the-following-configuration-key-is-missing-x'
				),
				[missingKeys.join(', ')]
			);
		}
	};

	const _validateJSON = (text, name) => {
		try {
			return JSON.parse(text);
		}
		catch {
			throw sub(Liferay.Language.get('x-is-invalid'), [name]);
		}
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		setIsSubmitting(true);

		const formData = new FormData(form.current);

		try {
			const parseElementTemplateJSON = _validateJSON(
				elementTemplateJSON,
				Liferay.Language.get('element-template-json')
			);
			const parseUIConfigurationJSON = _validateJSON(
				uiConfigurationJSON,
				Liferay.Language.get('ui-configuration-json')
			);

			_validateConfigKeys(elementTemplateJSON, parseUIConfigurationJSON);

			if (!isNotEmpty(parseElementTemplateJSON.title)) {
				throw Liferay.Language.get('error.title-empty');
			}

			if (
				typeof parseElementTemplateJSON.title === 'object' &&
				!isNotEmpty(parseElementTemplateJSON.title[defaultLocale])
			) {
				throw Liferay.Language.get('error.default-locale-title-empty');
			}

			_appendEntryLocale(
				parseElementTemplateJSON.title,
				'title',
				formData
			);
			_appendEntryLocale(
				parseElementTemplateJSON.description,
				'description',
				formData
			);

			formData.append(
				`${namespace}configuration`,
				JSON.stringify({
					elementTemplateJSON: parseElementTemplateJSON,
					uiConfigurationJSON: parseUIConfigurationJSON,
				})
			);
		}
		catch (error) {
			openErrorToast({
				message: error,
			});

			setIsSubmitting(false);

			return;
		}

		formData.append(`${namespace}type`, blueprintType);
		formData.append(`${namespace}blueprintId`, blueprintId);
		formData.append(`${namespace}redirect`, redirectURL);

		return fetch(submitFormURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				if (
					Object.prototype.hasOwnProperty.call(
						responseContent,
						'errors'
					)
				) {
					responseContent.errors.forEach((message) =>
						openErrorToast({message})
					);

					setIsSubmitting(false);
				}
				else {
					navigate(redirectURL);
				}
			})
			.catch(() => {
				openErrorToast();

				setIsSubmitting(false);
			});
	};

	return (
		<>
			<form ref={form}>
				<div className="page-toolbar-root">
					<ClayToolbar light>
						<ClayLayout.ContainerFluid>
							<ClayToolbar.Nav>
								<ClayToolbar.Item
									className="text-left"
									expand
								/>

								<ClayToolbar.Item>
									<PreviewModal
										body={_renderPreviewBody()}
										size="lg"
										title={Liferay.Language.get(
											'preview-configuration'
										)}
									>
										<ClayButton
											borderless
											displayType="secondary"
											small
										>
											{Liferay.Language.get('preview')}
										</ClayButton>
									</PreviewModal>
								</ClayToolbar.Item>

								<ClayToolbar.Item>
									<ClayLink
										displayType="secondary"
										href={redirectURL}
										outline="secondary"
									>
										{Liferay.Language.get('cancel')}
									</ClayLink>
								</ClayToolbar.Item>

								<ClayToolbar.Item>
									<ClayButton
										disabled={isSubmitting}
										onClick={handleSubmit}
										small
										type="submit"
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								</ClayToolbar.Item>
							</ClayToolbar.Nav>
						</ClayLayout.ContainerFluid>
					</ClayToolbar>
				</div>
			</form>

			<div className="element-row">
				<ClayLayout.Row>
					<ClayLayout.Col size={8}>
						<div className="element-section">
							<div className="element-header">
								<ClayButton
									borderless
									className={expand && 'active'}
									disabled={false}
									displayType="secondary"
									monospaced
									onClick={() => setExpand(!expand)}
									small
									title={Liferay.Language.get(
										'predefined-variables'
									)}
									type="submit"
								>
									<ClayIcon symbol="list-ul" />
								</ClayButton>

								<div className="expand-header">
									<div className="header-label">
										<label>
											{Liferay.Language.get(
												'element-template-json'
											)}
											<ClayTooltipProvider>
												<ClaySticker
													className="cursor-pointer"
													displayType="unstyled"
													size="sm"
												>
													<ClayIcon
														data-tooltip-align="top"
														symbol="info-circle"
														title={Liferay.Language.get(
															'element-template-json-helptext'
														)}
													/>
												</ClaySticker>
											</ClayTooltipProvider>
										</label>
									</div>
								</div>
							</div>

							<ClayLayout.Row>
								<ClayLayout.Col
									className={getCN('json-section', {
										hide: !expand,
									})}
									size={3}
								>
									<div className="sidebar sidebar-light">
										<div className="sidebar-header">
											<span className="text-truncate-inline">
												<span className="text-truncate">
													{Liferay.Language.get(
														'predefined-variables'
													)}
												</span>
											</span>
										</div>

										<div className="container-fluid sidebar-input">
											<SearchInput
												onChange={_handleSearchFilter}
											/>
										</div>

										<div className="container-fluid">
											<dl className="sidebar-dl">
												{variables.length ? (
													variables
														.filter(
															(item) =>
																item
																	.parameterDefinitions
																	.length
														)
														.map((item) => (
															<SidebarPanel
																categoryName={
																	item.categoryName
																}
																handleClick={
																	_onVariableClick
																}
																item={item}
																key={
																	item.categoryName
																}
																parameterDefinitions={
																	item.parameterDefinitions
																}
															/>
														))
												) : (
													<div className="empty-list-message">
														<ClayEmptyState
															title={Liferay.Language.get(
																'no-predefined-variables-found'
															)}
														/>
													</div>
												)}
											</dl>
										</div>
									</div>
								</ClayLayout.Col>

								<ClayLayout.Col
									className="json-section"
									size={expand ? 9 : 12}
								>
									<CodeMirrorEditor
										onChange={(value) =>
											setElementTemplateJSON(value)
										}
										ref={elementTemplateJSONRef}
										value={elementTemplateJSON}
									/>
								</ClayLayout.Col>
							</ClayLayout.Row>
						</div>
					</ClayLayout.Col>

					<ClayLayout.Col size={4}>
						<div className="element-section">
							<div className="element-header">
								<div className="expand-header">
									<div className="header-label">
										<label>
											{Liferay.Language.get(
												'ui-configuration-json'
											)}
											<ClayTooltipProvider>
												<ClaySticker
													className="cursor-pointer"
													displayType="unstyled"
													size="sm"
												>
													<ClayIcon
														data-tooltip-align="top"
														symbol="info-circle"
														title={Liferay.Language.get(
															'ui-configuration-json-helptext'
														)}
													/>
												</ClaySticker>
											</ClayTooltipProvider>
										</label>
									</div>
								</div>
							</div>

							<div className="json-section">
								<CodeMirrorEditor
									onChange={(value) =>
										setUIConfigurationJSON(value)
									}
									value={uiConfigurationJSON}
								/>
							</div>
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</div>
		</>
	);
}

EditElementForm.propTypes = {
	blueprintId: PropTypes.string,
	blueprintType: PropTypes.number,
	initialConfigurationString: PropTypes.string,
	initialDescription: PropTypes.object,
	initialTitle: PropTypes.object,
	predefinedVariables: PropTypes.arrayOf(PropTypes.object),
	redirectURL: PropTypes.string,
	submitFormURL: PropTypes.string,
};

function SidebarPanel({categoryName, handleClick, parameterDefinitions}) {
	const [expand, setExpand] = useState(false);

	return (
		<div>
			<ClayButton
				className="panel-header sidebar-dt"
				displayType="unstyled"
				onClick={() => setExpand(!expand)}
			>
				<span>{categoryName}</span>

				<span className="sidebar-arrow">
					<ClayIcon symbol={expand ? 'angle-down' : 'angle-right'} />
				</span>
			</ClayButton>

			{expand &&
				parameterDefinitions.map((entry) => (
					<dd className="sidebar-dd" key={entry.variable}>
						<ClayTooltipProvider>
							<ClayButton
								className="nav-link"
								displayType="unstyled"
								onClick={() => handleClick(entry.variable)}
								title={entry.variable}
							>
								{entry.description}
							</ClayButton>
						</ClayTooltipProvider>
					</dd>
				))}
		</div>
	);
}

export default function ({context, props}) {
	return (
		<ThemeContext.Provider value={context}>
			<div className="edit-element-root">
				<ErrorBoundary>
					<EditElementForm {...props} />
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
