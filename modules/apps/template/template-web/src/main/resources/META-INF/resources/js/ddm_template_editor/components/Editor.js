/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, navigate, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from './AppContext';
import CodeMirrorEditor from './CodeMirrorEditor';

export function Editor({autocompleteData, initialScript, mode}) {
	const {inputChannel, portletNamespace} = useContext(AppContext);

	const [script, setScript] = useState(initialScript);

	const scriptRef = useRef(script);
	scriptRef.current = script;

	const codeMirrorRef = useRef(null);

	useEffect(() => {
		const refreshHandler = Liferay.on(
			`${portletNamespace}refreshEditor`,
			() => {
				const formElement = document.getElementById(
					`${portletNamespace}fm`
				);

				if (!formElement) {
					return;
				}

				if (scriptRef.current === initialScript) {
					setScript('');

					codeMirrorRef.current?.setValue('');
				}

				Liferay.fire(`${portletNamespace}saveTemplate`);

				requestAnimationFrame(() => {
					formElement.action = window.location.href;
					formElement.submit();
				});
			}
		);

		return () => {
			refreshHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	useEffect(() => {
		const scriptImportedHandler = Liferay.on(
			`${portletNamespace}scriptImported`,
			(event) => {
				setScript(event.script);

				codeMirrorRef.current?.setValue(event.script);

				openToast({
					message: sub(
						Liferay.Language.get('x-imported'),
						event.fileName
					),
					title: Liferay.Language.get('success'),
					type: 'success',
				});
			}
		);

		return () => {
			scriptImportedHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	useEffect(() => {
		const saveAndContinueButton = document.getElementById(
			`${portletNamespace}saveAndContinueButton`
		);

		const saveButton = document.getElementById(
			`${portletNamespace}saveButton`
		);

		const saveTemplate = (redirect) => {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (!redirect) {
				const saveAndContinueInput = document.getElementById(
					`${portletNamespace}saveAndContinue`
				);

				saveAndContinueInput.value = true;
			}

			const changeDisabled = (disabled) => {
				saveButton.disabled = disabled;
				saveAndContinueButton.disabled = disabled;
			};

			const formData = new FormData(form);

			formData.append(
				`${portletNamespace}scriptContent`,
				new File([new Blob([script])], 'scriptContent')
			);

			changeDisabled(true);

			const liferayForm = Liferay.Form.get(form.id);

			if (liferayForm) {
				const validator = liferayForm.formValidator;

				validator.validate();

				if (validator.hasErrors()) {
					validator.focusInvalidField();
					changeDisabled(false);

					return;
				}
			}

			fetch(form.action, {body: formData, method: 'POST'})
				.then((response) => {
					if (response.redirected) {
						navigate(response.url);
					}

					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						title: Liferay.Language.get('success'),
						type: 'success',
					});

					changeDisabled(false);

					return response;
				})
				.then((response) => response.json())
				.then(({error}) => {
					if (error) {
						openToast({
							message: Liferay.Language.get(error),
							title: Liferay.Language.get('error'),
							type: 'danger',
						});
					}
				})
				.catch(() => {
					changeDisabled(false);
				});
		};

		const onSaveAndContinueButtonClick = (event) => {
			event.preventDefault();

			saveTemplate(false);
		};

		const onSaveButtonClick = (event) => {
			event.preventDefault();

			saveTemplate(true);
		};

		if (saveAndContinueButton) {
			saveAndContinueButton.addEventListener(
				'click',
				onSaveAndContinueButtonClick
			);
		}

		if (saveButton) {
			saveButton.addEventListener('click', onSaveButtonClick);
		}

		return () => {
			if (saveAndContinueButton) {
				saveAndContinueButton.removeEventListener(
					'click',
					onSaveAndContinueButtonClick
				);
			}
			if (saveButton) {
				saveButton.removeEventListener('click', onSaveButtonClick);
			}
		};
	}, [portletNamespace, script]);

	useEffect(() => {
		const exportScriptHandler = Liferay.on(
			`${portletNamespace}exportScript`,
			() => {
				exportScript(scriptRef.current, 'ftl');
			}
		);

		return () => {
			exportScriptHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	return (
		<>
			<CodeMirrorEditor
				autocompleteData={autocompleteData}
				content={initialScript}
				inputChannel={inputChannel}
				mode={mode}
				onChange={setScript}
				ref={codeMirrorRef}
			/>
		</>
	);
}

Editor.propTypes = {
	autocompleteData: PropTypes.object.isRequired,
	initialScript: PropTypes.string.isRequired,
	mode: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.shape({
			globalVars: PropTypes.bool.isRequired,
			name: PropTypes.string.isRequired,
		}),
	]),
};

const exportScript = (script) => {
	const link = document.createElement('a');
	const blob = new Blob([script]);

	const fileURL = URL.createObjectURL(blob);

	link.href = fileURL;
	link.download = 'script.ftl';

	link.click();

	URL.revokeObjectURL(fileURL);
};
