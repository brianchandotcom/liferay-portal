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

const Header = ({greetings, helper, title}) => {
	return (
		<header className="p-4">
			{greetings && (
				<h6 className="mb-1 text-brand-primary text-small-caps">
					{greetings}
				</h6>
			)}

			<h2
				className={classNames('text-neutral-10', {
					'mb-0': !helper,
					'mb-1': helper,
				})}
			>
				{title}
			</h2>

			{helper && (
				<p className="mb-0 text-neutral-7 text-paragraph-sm">
					{helper}
				</p>
			)}
		</header>
	);
};

export default Header;
