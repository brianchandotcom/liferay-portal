/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
