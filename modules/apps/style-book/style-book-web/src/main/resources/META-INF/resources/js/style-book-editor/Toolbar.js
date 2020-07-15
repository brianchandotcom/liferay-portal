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
import classNames from 'classnames';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useContext} from 'react';

import {StyleBookContext} from './StyleBookContext';
import {config} from './config';
import {DRAFT_STATUS} from './constants/draftStatusConstants';

const STATUS_TO_LABEL = {
	[DRAFT_STATUS.draftSaved]: Liferay.Language.get('draft-saved'),
	[DRAFT_STATUS.notSaved]: '',
	[DRAFT_STATUS.saving]: `${Liferay.Language.get('saving')}...`,
};

export default function Toolbar() {
	const {draftStatus} = useContext(StyleBookContext);

	const onClick = () => {
		const body = objectToFormData({
			[`${config.namespace}styleBookEntryId`]: config.styleBookEntryId,
		});

		fetch(config.publishURL, {body, method: 'POST'});
	};

	return (
		<div className="p-3 style-book-editor__toolbar">
			<div>
				{draftStatus === DRAFT_STATUS.draftSaved && (
					<ClayIcon
						className="mt-0 style-book-editor__status-icon"
						symbol="check-circle"
					/>
				)}
				<span
					className={classNames(
						'ml-1 style-book-editor__status-text',
						{
							'text-success':
								draftStatus === DRAFT_STATUS.draftSaved,
						}
					)}
				>
					{STATUS_TO_LABEL[draftStatus]}
				</span>
			</div>
			<ClayButton displayType="primary" onClick={onClick} small>
				{Liferay.Language.get('publish')}
			</ClayButton>
		</div>
	);
}
