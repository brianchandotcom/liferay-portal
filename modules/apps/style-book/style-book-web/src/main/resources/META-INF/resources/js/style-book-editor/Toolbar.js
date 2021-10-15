/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {ALIGN_POSITIONS, align} from 'frontend-js-web';
import React, {useContext, useLayoutEffect, useRef, useState} from 'react';

import LayoutSelector from './LayoutSelector';
import {StyleBookContext} from './StyleBookContext';
import {config} from './config';
import {DRAFT_STATUS} from './constants/draftStatusConstants';

const STATUS_TO_LABEL = {
	[DRAFT_STATUS.draftSaved]: Liferay.Language.get('draft-saved'),
	[DRAFT_STATUS.notSaved]: '',
	[DRAFT_STATUS.saving]: `${Liferay.Language.get('saving')}...`,
};

export default function Toolbar() {
	return (
		<div className="management-bar navbar style-book-editor__toolbar">
			<ClayLayout.ContainerFluid>
				<ul className="navbar-nav start">
					<li className="nav-item">
						<span className="style-book-editor__page-preview-text">
							{`${Liferay.Language.get('preview')}:`}
						</span>
						<LayoutSelector />
					</li>
				</ul>

				<ul className="end navbar-nav">
					<li className="mr-3 nav-item">
						<DraftStatus />
					</li>
					<li className="mx-3 nav-item">
						<HelpInformation />
					</li>
					<li className="ml-3 nav-item">
						<PublishButton />
					</li>
				</ul>
			</ClayLayout.ContainerFluid>
		</div>
	);
}

function DraftStatus() {
	const {draftStatus} = useContext(StyleBookContext);

	return (
		<div>
			{draftStatus === DRAFT_STATUS.draftSaved && (
				<ClayIcon
					className="mt-0 style-book-editor__status-icon"
					symbol="check-circle"
				/>
			)}
			<span
				className={classNames('ml-1 style-book-editor__status-text', {
					'text-success': draftStatus === DRAFT_STATUS.draftSaved,
				})}
			>
				{STATUS_TO_LABEL[draftStatus]}
			</span>
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

function PublishButton() {
	const handleSubmit = (event) => {
		if (
			!confirm(
				Liferay.Language.get(
					'once-published,-these-changes-will-affect-all-instances-of-the-site-using-these-properties'
				)
			)
		) {
			event.preventDefault();
		}
	};

	return (
		<form action={config.publishURL} method="POST">
			<input
				name={`${config.namespace}redirect`}
				type="hidden"
				value={config.redirectURL}
			/>
			<input
				name={`${config.namespace}styleBookEntryId`}
				type="hidden"
				value={config.styleBookEntryId}
			/>

			<ClayButton
				disabled={config.pending}
				displayType="primary"
				onClick={handleSubmit}
				small
				type="submit"
			>
				{Liferay.Language.get('publish')}
			</ClayButton>
		</form>
	);
}
