/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {flipThirdPartyCookiesOff} from '@liferay/cookies-banner-web';
import CKEditor from 'ckeditor4-react';
import {loadEditorClientExtensions} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {
	forwardRef,
	useCallback,
	useEffect,
	useRef,
	useState,
} from 'react';

import '../../css/main.scss';

const BASEPATH = '/o/frontend-editor-ckeditor-web/ckeditor/';
const CONTEXT_URL = Liferay.ThemeDisplay.getPathContext();
const CURRENT_PATH = CONTEXT_URL ? CONTEXT_URL + BASEPATH : BASEPATH;

function createElementFromHTML(htmlString) {
	const div = document.createElement('div');
	div.innerHTML = htmlString;

	return flipThirdPartyCookiesOff(div).innerHTML;
}

/**
 * This component contains shared code between
 * DXP implementations of CKEditor. Please don't import it directly.
 */
const BaseEditor = forwardRef(
	(
		{
			config: initialConfig,
			contents,
			name,
			onChange,
			onChangeMethodName,
			...props
		},
		ref
	) => {
		const [config, setConfig] = useState(initialConfig);
		const [loading, setLoading] = useState(false);

		const editorRef = useRef();
		const firstRenderRef = useRef(true);

		useEffect(() => {
			Liferay.once('beforeScreenFlip', () => {
				if (
					window.CKEDITOR &&
					!Object.keys(window.CKEDITOR.instances).length
				) {
					delete window.CKEDITOR;
				}
			});
		}, []);

		useEffect(() => {
			if (firstRenderRef.current) {
				firstRenderRef.current = false;
			}
			else {
				return;
			}

			if (!initialConfig.editorTransformerURLs) {
				return;
			}

			setLoading(true);

			loadEditorClientExtensions({
				config: initialConfig,
				onLoad: ({transformedConfig}) => {
					setConfig(transformedConfig);

					setLoading(false);
				},
			});
		}, [initialConfig]);

		const getHTML = useCallback(() => {
			let data = contents;

			const editor = editorRef.current.editor;

			if (editor && editor.instanceReady) {
				data = editor.getData();

				if (
					CKEDITOR.env.gecko &&
					CKEDITOR.tools.trim(data) === '<br />'
				) {
					data = '';
				}

				data = data.replace(/(\u200B){7}/, '');
			}

			return createElementFromHTML(data);
		}, [contents]);

		const onChangeCallback = () => {
			if (!onChangeMethodName && !onChange) {
				return;
			}

			if (onChangeMethodName) {
				window[onChangeMethodName](getHTML());
			}
			else {
				onChange(getHTML());
			}
		};

		const editorRefsCallback = useCallback(
			(element) => {
				if (ref) {
					ref.current = element;
				}
				editorRef.current = element;
			},
			[ref, editorRef]
		);

		useEffect(() => {
			window[name] = {
				getHTML,
				getText() {
					return contents;
				},
			};

			return () => {
				delete window[name];
			};
		}, [contents, getHTML, name]);

		return loading ? (
			<ClayLoadingIndicator />
		) : (
			<CKEditor
				config={config}
				name={name}
				onChange={onChangeCallback}
				onChangeMethodName={onChangeMethodName}
				ref={editorRefsCallback}
				{...props}
			/>
		);
	}
);

CKEditor.editorUrl = `${CURRENT_PATH}ckeditor.js`;
window.CKEDITOR_BASEPATH = CURRENT_PATH;

BaseEditor.displayName = 'BaseEditor';

BaseEditor.propTypes = {
	contents: PropTypes.string,
	name: PropTypes.string.isRequired,
	onChange: PropTypes.func,
	onChangeMethodName: PropTypes.string,
};

export default BaseEditor;
