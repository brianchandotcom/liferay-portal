import ErrorPage from '../pages/ErrorPage';
import React from 'react';
import withCurrentUser from './WithCurrentUser';
import {compose} from 'redux';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from '../util/router';
import {User} from '../util/records';
import {UserRoleNames} from 'shared/util/constants';

/**
 * Creates an HOC that renders a component only if the current user's role
 * exists in the passed roles. Otherwise, it will render an appropriate
 * error page.
 * @param {Array.<string>} roles - The roles to check for.
 * @returns {Function} - The resulting HOC.
 */
const withPermission = roles =>
	compose(
		withCurrentUser,
		WrappedComponent =>
			class WithPermission extends React.Component {
				static propTypes = {
					currentUser: PropTypes.instanceOf(User).isRequired
				};

				render() {
					const {className, currentUser, ...otherProps} = this.props;

					const hasPermission = roles.includes(currentUser.roleName);

					if (hasPermission) {
						return (
							<WrappedComponent
								{...otherProps}
								className={className}
								currentUser={currentUser}
							/>
						);
					} else {
						return (
							<ErrorPage
								className={className}
								href={toRoute(Routes.BASE)}
								message={Liferay.Language.get(
									'you-do-not-have-permission-to-view-this-resource'
								)}
								subtitle={Liferay.Language.get(
									'invalid-permission'
								)}
								title='403'
							/>
						);
					}
				}
			}
	);

/**
 * An HOC that will render an error page if the current user does
 * not have some kind of admin permission. Currently, this means
 * they are either an "administrator" or an "owner".
 * @param {Component}
 * @returns {Component}
 */
export const withAdminPermission = withPermission([
	UserRoleNames.Administrator,
	UserRoleNames.Owner
]);

export default withPermission;
