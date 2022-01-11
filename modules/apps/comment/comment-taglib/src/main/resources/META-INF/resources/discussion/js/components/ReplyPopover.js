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

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import useOnClickOutside from '../hooks/useOnClickOutside';
import UserIcon from './UserIcon';

import '../../css/main.scss';

function ReplyPopover({
	ariaLabel,
	contentHTML,
	href = '#',
	portraitURL,
	time,
	userId,
	username,
}) {
	const [openPopover, setOpenPopover] = useState(false);

	useOnClickOutside(
		['.lfr-discussion-reply-popover', '.lfr-discussion-parent-link'],
		() => setOpenPopover(false)
	);

	return (
		<ClayPopover
			alignPosition={Liferay.Browser.isMobile() ? 'top-left' : 'top'}
			className="lfr-discussion-reply-popover"
			header={
				<ClayLayout.ContentRow noGutters="x" padded>
					<ClayLayout.ContentCol>
						<UserIcon
							fullName={username}
							portraitURL={portraitURL}
							userId={userId}
						/>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol expand>
						<div className="username">{username}</div>

						<div className="font-weight-normal text-secondary">
							{time}
						</div>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			}
			onShowChange={setOpenPopover}
			show={openPopover}
			trigger={
				<a
					aria-label={ariaLabel}
					className="lfr-discussion-parent-link"
					href={href}
					onClick={(event) => {
						event.preventDefault();
						setOpenPopover((open) => !open);
					}}
				>
					<ClayIcon
						className="inline-item inline-item-before"
						small="true"
						symbol="redo"
					/>

					{username}
				</a>
			}
		>
			<div dangerouslySetInnerHTML={{__html: contentHTML}}></div>
		</ClayPopover>
	);
}

ReplyPopover.propTypes = {
	ariaLabel: PropTypes.string,
	contentHTML: PropTypes.string.isRequired,
	href: PropTypes.string,
	time: PropTypes.string.isRequired,
	username: PropTypes.string.isRequired,
};

export default ReplyPopover;
