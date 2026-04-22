/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {cancelDebounce, debounce} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';

const DISALLOWED_EDITABLE_WORDS = [
	'data-lfr-editable-id',
	'data-lfr-editable-type',
	'lfr-editable',
];

const ERROR_GUTTER = 'CodeMirror-error';

const VIEW_TYPES = {
	columns: 1,
	fullscreen: 2,
	rows: 3,
};

const HTMLEditorModal = ({
	initialContent = '',
	onClose: onCloseCallback,
	onSave,
}) => {
	const [content, setContent] = useState(initialContent);
	const [showError, setShowError] = useState(false);
	const [viewType, setViewType] = useState(VIEW_TYPES.columns);
	const [visible, setVisible] = useState(true);

	const editorRef = useRef();
	const marksRef = useRef([]);

	useEffect(() => {
		const codeMirror = editorRef.current;

		if (!codeMirror) {
			return;
		}

		const gutters = codeMirror.getOption('gutters');

		if (!gutters.includes(ERROR_GUTTER)) {
			codeMirror.setOption('gutters', [ERROR_GUTTER, ...gutters]);
		}

		debouncedRefreshMarkers(codeMirror, content, marksRef);

		return () => cancelDebounce(debouncedRefreshMarkers);
	}, [content]);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			onCloseCallback();
		},
	});

	return (
		visible && (
			<ClayModal observer={observer} size="full-screen">
				<ClayModal.Header
					className="align-items-center cadmin d-flex"
					withTitle={false}
				>
					<div className="col p-0">
						<span>{Liferay.Language.get('edit-content')}</span>
					</div>

					<ClayButton.Group>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'display-vertically'
							)}
							displayType="secondary"
							onClick={() => setViewType(VIEW_TYPES.columns)}
							size="sm"
							symbol="columns"
							title={Liferay.Language.get('display-vertically')}
						/>

						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'display-horizontally'
							)}
							displayType="secondary"
							onClick={() => setViewType(VIEW_TYPES.rows)}
							size="sm"
							symbol="cards"
							title={Liferay.Language.get('display-horizontally')}
						/>

						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('full-screen')}
							displayType="secondary"
							onClick={() => setViewType(VIEW_TYPES.fullscreen)}
							size="sm"
							symbol="expand"
							title={Liferay.Language.get('full-screen')}
						/>
					</ClayButton.Group>

					<div className="col d-flex justify-content-end p-0">
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('close')}
							className="close"
							displayType="unstyled"
							onClick={onClose}
							symbol="times"
						/>
					</div>
				</ClayModal.Header>

				<ClayModal.Body className="p-0">
					{showError && (
						<ClayAlert
							className="mx-3 my-4"
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{Liferay.Language.get(
								'it-is-not-allowed-to-add-fragment-editable-elements'
							)}
						</ClayAlert>
					)}

					<div
						className={classNames(
							'd-flex page-editor__html-editor-modal__editor-container h-100',
							{
								'flex-column': viewType === VIEW_TYPES.rows,
							}
						)}
					>
						<div
							className={classNames({
								'h-50 w-100': viewType === VIEW_TYPES.rows,
								'h-100 w-50': viewType === VIEW_TYPES.columns,
								'h-100 w-100':
									viewType === VIEW_TYPES.fullscreen,
							})}
						>
							<CodeMirrorEditor
								initialContent={initialContent}
								onChange={(nextContent) => {
									setContent(nextContent);
									setShowError(false);
								}}
								ref={editorRef}
							/>
						</div>

						{viewType !== VIEW_TYPES.fullscreen && (
							<div
								className={classNames({
									'page-editor__html-editor-modal__preview-columns h-100 px-3 w-50':
										viewType === VIEW_TYPES.columns,
									'page-editor__html-editor-modal__preview-rows h-50 py-2 w-100':
										viewType === VIEW_TYPES.rows,
								})}
							>
								<div
									dangerouslySetInnerHTML={{__html: content}}
									onClick={(event) => event.preventDefault()}
								/>
							</div>
						)}
					</div>
				</ClayModal.Body>

				<ClayModal.Footer
					className="cadmin"
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => {
									onClose();
								}}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								onClick={() => {
									if (
										DISALLOWED_EDITABLE_WORDS.some((word) =>
											content.includes(word)
										)
									) {
										setShowError(true);

										return;
									}

									onSave(content);
									onClose();
								}}
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		)
	);
};

const findDisallowedRanges = (content) => {
	const ranges = [];
	const lines = content.split('\n');

	for (let line = 0; line < lines.length; line++) {
		const text = lines[line];

		DISALLOWED_EDITABLE_WORDS.forEach((word) => {
			let index = text.indexOf(word);

			while (index !== -1) {
				ranges.push({
					from: {ch: index, line},
					to: {ch: index + word.length, line},
				});

				index = text.indexOf(word, index + word.length);
			}
		});
	}

	return ranges;
};

const debouncedRefreshMarkers = debounce((codeMirror, content, marksRef) => {
	marksRef.current.forEach((mark) => mark.clear());
	marksRef.current = [];

	codeMirror.clearGutter(ERROR_GUTTER);

	const ranges = findDisallowedRanges(content);

	if (!ranges.length) {
		return;
	}

	const errorLabel = Liferay.Language.get(
		'fragment-editable-elements-are-not-allowed'
	);

	ranges.forEach((range) => {
		marksRef.current.push(
			codeMirror.markText(range.from, range.to, {
				className: 'text-danger',
				title: errorLabel,
			})
		);
	});

	const element = document.createElement('div');

	element.className = 'lfr-portal-tooltip ml-1 mt-1 text-danger';
	element.dataset.title = errorLabel;
	element.dataset.tooltipAlign = 'right';
	element.setAttribute('aria-label', errorLabel);
	element.setAttribute('role', 'img');
	element.setAttribute('tabindex', '0');

	element.innerHTML = `
		<svg class="lexicon-icon lexicon-icon-exclamation-full mt-0" focusable="false">
			<use href="${Liferay.Icons.spritemap}#exclamation-full" />
		</svg>`;

	codeMirror.setGutterMarker(ranges[0].from.line, ERROR_GUTTER, element);
}, 200);

export default HTMLEditorModal;
