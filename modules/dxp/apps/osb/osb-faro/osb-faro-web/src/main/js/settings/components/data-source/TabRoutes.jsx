import BundleRouter from 'route-middleware/BundleRouter';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';
import {Switch} from 'react-router-dom';

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
			<Switch>
				{routes.map(({component: Component, path}) => (
					<BundleRouter
						componentProps={omitDefinedProps(
							otherProps,
							TabRoutes.propTypes
						)}
						data={Component}
						exact
						key={path}
						path={path}
					/>
				))}
			</Switch>
		);
	}
}
