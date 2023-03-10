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

import {Outlet} from 'react-router-dom';

import HeaderContextProvider from '../../context/HeaderContext';
import Sidebar from '../Sidebar';
import Header from './Header';

const Layout = () => (
	<main className="tr-main">
		<div className="tr-main__body">
			<Sidebar />

			<div className="tr-main__body__page">
				<HeaderContextProvider>
					<Header />

					<section className="tr-main__body__page__content">
						<Outlet />
					</section>
				</HeaderContextProvider>
			</div>
		</div>
	</main>
);

export default Layout;
