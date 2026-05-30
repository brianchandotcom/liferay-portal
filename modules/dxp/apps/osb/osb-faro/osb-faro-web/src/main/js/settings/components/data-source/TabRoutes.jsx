import BundleRouter from 'route-middleware/BundleRouter';
import ErrorPage from 'shared/pages/ErrorPage';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';
import {Route, Routes as RouterRoutes} from 'react-router-dom';

export default class TabRoutes extends React.Component {
	static propTypes = {
		routes: PropTypes.arrayOf(
			PropTypes.shape({
				component: PropTypes.func,
				path: PropTypes.string
			})
		)
	};

	render() {
		const {routes, ...otherProps} = this.props;

		return (
			<RouterRoutes>
				{routes.map(({component: Component, path}) => (
					<Route
						element={
							<BundleRouter
								componentProps={omitDefinedProps(
									otherProps,
									TabRoutes.propTypes
								)}
								data={Component}
							/>
						}
						key={path}
						path={path}
					/>
				))}

				<Route element={<ErrorPage />} path='*' />
			</RouterRoutes>
		);
	}
}
