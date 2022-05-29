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

import classNames from 'classnames';
import {memo, useEffect} from 'react';
import {Link, useMatch, useResolvedPath} from 'react-router-dom';
import {Button} from '../../../../../../common/components';
import {useAppPropertiesContext} from '../../../../../../common/context/AppPropertiesProvider';

const MenuItem = ({children, iconKey, setActive, to}) => {
	const {liferayWebDAV} = useAppPropertiesContext();
	const isActive = !!useMatch({path: useResolvedPath(to)?.pathname});

	useEffect(() => {
		if (setActive) {
			setActive(isActive);
		}
	}, [isActive, setActive]);

	return (
		<li>
			<Link to={to}>
				<Button
					className={classNames(
						'btn-borderless mb-1 px-3 py-2 rounded text-neutral-10',
						{
							'align-items-center d-flex mt-1': !!iconKey,
							'cp-menu-btn-active': isActive,
						}
					)}
					isImagePrependIcon={!!iconKey}
					prependIcon={
						iconKey &&
						`${liferayWebDAV}/assets/navigation-menu/${iconKey}_icon${
							isActive ? '' : '_gray'
						}.svg`
					}
				>
					{children}
				</Button>
			</Link>
		</li>
	);
};

export default memo(MenuItem);
