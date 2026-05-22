/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ResizeHandle} from '@clayui/core';
import {useObservedMaxWidth} from '@clayui/shared';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useSessionState} from 'frontend-js-components-web';
import {sessionStorage} from 'frontend-js-web';
import React, {useCallback, useEffect, useId, useRef, useState} from 'react';

import {
	EVENT_CLOSE_PREVIEW,
	EVENT_HANDLE_PREVIEW,
} from './ContentEditorToolbar';
import PreviewBody from './preview/PreviewBody';
import PreviewHeader from './preview/PreviewHeader';
import {
	PREVIEW_VISIBLE_SESSION_KEY,
	PREVIEW_WIDTH_SESSION_KEY,
} from './preview/sessionKeys';
import useIsContentEdited from './useIsContentEdited';
import useLocalizationLanguageId from './useLocalizationLanguageId';

import '../../../css/content_editor/ContentEditorPreview.scss';

const BREAKPOINT_LG = 992;
const FORM_SELECTOR = '.lfr-layout-structure-item-form';
const PREVIEW_WIDTH_MIN = 500;

export default function ContentEditorPreview({
	defaultLanguageId,
	getPreviewDataURL,
	title,
}: {
	defaultLanguageId: Liferay.Language.Locale;
	getPreviewDataURL: string;
	title: string;
}) {
	const isContentEdited = useIsContentEdited(FORM_SELECTOR);
	const [isVisible, setIsVisible] = useState<boolean>(
		() =>
			sessionStorage.getItem(
				PREVIEW_VISIBLE_SESSION_KEY,
				sessionStorage.TYPES.NECESSARY
			) === 'true'
	);
	const [resizeWidth, setResizeWidth] = useSessionState(
		PREVIEW_WIDTH_SESSION_KEY,
		window.innerWidth / 2
	);
	const [resizing, setResizing] = useState<boolean>(false);

	const contentRef = useRef<HTMLElement | null>(null);
	const previewRef = useRef<HTMLDivElement>(null);
	const sidePanelBarRef = useRef<HTMLElement | null>(null);

	const localizationLanguageId = useLocalizationLanguageId(defaultLanguageId);

	const previewWidthMax = useObservedMaxWidth(previewRef);
	const previewWidth = Math.min(previewWidthMax, resizeWidth!);
	const titleId = useId();

	useEffect(() => {
		contentRef.current = document.querySelector('#content');
		sidePanelBarRef.current = document.querySelector(
			'.content-editor__side-panel'
		);

		const handlePreview = ({showPreview}: {showPreview: boolean}) =>
			setIsVisible(showPreview);

		Liferay.on(EVENT_HANDLE_PREVIEW, handlePreview);

		return () => Liferay.detach(EVENT_HANDLE_PREVIEW, handlePreview);
	}, []);

	useEffect(() => {
		if (!isVisible) {
			sidePanelBarRef.current?.style.removeProperty('right');
			contentRef.current?.style.removeProperty('padding-right');
		}
		else {
			if (contentRef.current) {
				contentRef.current.style.paddingRight = `${previewWidth}px`;
			}

			if (sidePanelBarRef.current) {
				sidePanelBarRef.current.style.right = `${previewWidth}px`;
			}
		}
	}, [isVisible, previewWidth]);

	useEventListener('mouseup', () => setResizing(false), true, window);

	useEventListener(
		'resize',
		useCallback(
			() =>
				setIsVisible(
					(isVisible) =>
						isVisible &&
						window.document.body.clientWidth >= BREAKPOINT_LG
				),
			[]
		),
		true,
		window
	);

	if (!Liferay.FeatureFlags['LPD-44507']) {
		return null;
	}

	return (
		<div
			aria-labelledby={titleId}
			className={classNames('content-editor__preview', {
				resizing,
				visible: isVisible,
			})}
			onTransitionEnd={({propertyName}) => {
				if (isVisible && propertyName === 'visibility') {
					previewRef.current?.focus();
				}
			}}
			ref={previewRef}
			style={{width: previewWidth}}
			tabIndex={-1}
		>
			{isVisible ? (
				<>
					<PreviewHeader
						onClosePreview={() => {
							Liferay.fire(EVENT_CLOSE_PREVIEW);

							setIsVisible(false);
						}}
						title={title}
						titleId={titleId}
					/>
					<PreviewBody
						getPreviewDataURL={getPreviewDataURL}
						isContentEdited={isContentEdited}
						languageId={localizationLanguageId}
					/>
				</>
			) : null}

			<ResizeHandle
				maxWidth={previewWidthMax}
				minWidth={PREVIEW_WIDTH_MIN}
				onWidthChange={(width: number) => {
					setResizeWidth(width);
					setResizing(true);
				}}
				position="right"
				width={previewWidth}
			/>
		</div>
	);
}
