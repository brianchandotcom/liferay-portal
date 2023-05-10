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
import Footer from './Footer';
import Header from './Header';

const Layout = ({
	children,
	className,
	footerProps,
	headerProps,
	headerSkeleton,
	layoutType = 'onboarding',
}) => (
	<div
		className={classNames(
			'border d-flex flex-column mx-auto rounded-lg shadow-lg',
			layoutType
		)}
	>
		{headerProps ? <Header {...headerProps} /> : headerSkeleton}

		<main className={classNames('flex-grow-1 overflow-auto', className)}>
			{children}
		</main>

		<Footer {...footerProps} />
	</div>
);
export default Layout;
