/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {ALIGN_POSITIONS, align} from 'frontend-js-web';
import React, {useLayoutEffect, useRef, useState} from 'react';

import PreviewSelector from './PreviewSelector';
import PublishButton from './PublishButton';
import Undo from './Undo';
import UndoHistory from './UndoHistory';
import {DRAFT_STATUS} from './constants/draftStatusConstants';
import {usePreviewLayout} from './contexts/LayoutContext';
import {useDraftStatus} from './contexts/StyleBookEditorContext';

const STATUS_TO_LABEL = {
	[DRAFT_STATUS.draftSaved]: Liferay.Language.get('saved'),
	[DRAFT_STATUS.notSaved]: '',
	[DRAFT_STATUS.saving]: `${Liferay.Language.get('saving')}...`,
};

export default React.memo(function Toolbar() {
	const previewLayout = usePreviewLayout();

	return (
		<div className="management-bar navbar style-book-editor__toolbar">
			<ClayLayout.ContainerFluid>
				<ul className="navbar-nav start">
					{previewLayout?.url && (
						<li className="nav-item">
							<span className="font-weight-bold">
								{`${Liferay.Language.get('preview')}`}
							</span>

							<PreviewSelector />
						</li>
					)}
				</ul>

				<ul className="end navbar-nav">
					<li className="mr-2 nav-item">
						<DraftStatus />
					</li>

					<li className="nav-item">
						<Undo />
					</li>

					<li className="nav-item">
						<UndoHistory />
					</li>

					<li className="mx-2 nav-item">
						<HelpInformation />
					</li>

					<li className="ml-2 nav-item">
						<PublishButton />
					</li>
				</ul>
			</ClayLayout.ContainerFluid>
		</div>
	);
});

function DraftStatus() {
	const draftStatus = useDraftStatus();

	return (
		<div>
			<span
				className={classNames('mx-1 style-book-editor__status-text', {
					'text-success': draftStatus === DRAFT_STATUS.draftSaved,
				})}
			>
				{STATUS_TO_LABEL[draftStatus]}
			</span>

			{draftStatus === DRAFT_STATUS.draftSaved && (
				<ClayIcon
					className="mx-1 style-book-editor__status-icon"
					symbol="check-circle"
				/>
			)}
		</div>
	);
}

function HelpInformation() {
	const [isShowPopover, setIsShowPopover] = useState(false);
	const popoverRef = useRef(null);
	const helpIconRef = useRef(null);

	useLayoutEffect(() => {
		if (isShowPopover) {
			align(
				popoverRef.current,
				helpIconRef.current,
				ALIGN_POSITIONS.Bottom,
				false
			);
		}
	}, [isShowPopover]);

	return (
		<span className="d-block text-secondary">
			<ClayIcon
				onMouseEnter={() => setIsShowPopover(true)}
				onMouseLeave={() => setIsShowPopover(false)}
				ref={helpIconRef}
				symbol="question-circle"
			/>

			{isShowPopover && (
				<ClayPopover
					alignPosition="bottom"
					header={Liferay.Language.get('help-information')}
					ref={popoverRef}
				>
					{Liferay.Language.get(
						'edit-the-style-book-using-the-sidebar-form.-you-can-preview-the-changes-instantly'
					)}
				</ClayPopover>
			)}
		</span>
	);
}
