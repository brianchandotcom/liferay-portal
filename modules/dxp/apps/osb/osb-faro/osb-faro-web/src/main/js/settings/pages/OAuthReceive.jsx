import Loading from 'shared/components/Loading';
import React from 'react';
import {emitAuthCode, emitError, emitToken} from 'shared/util/oauth';
import {PropTypes} from 'prop-types';

/**
 * This page serves as the view for the OAuth callback. It basically
 * receives the OAuth info as query params:
 *
 * @example
 * /oauth/receive?oauth_token=foo&oauth_verifier=bar
 *
 * @example
 * /oauth/receive?error=foo&error_description=bar
 *
 * Then it posts these credentials or errors to the window via an event emitter
 * function so that the opening window can receive them and close the window that
 * viewed this page.
 *
 * @class
 */
export default class OAuthReceive extends React.Component {
	static defaultProps = {
		error_description: Liferay.Language.get('authentication-error')
	};

	static propTypes = {
		code: PropTypes.string,
		error: PropTypes.string,
		error_description: PropTypes.string,
		oauth_token: PropTypes.string,
		oauth_verifier: PropTypes.string
	};

	componentDidMount() {
		/* eslint-disable camelcase */
		const {
			code,
			error,
			error_description,
			oauth_token,
			oauth_verifier
		} = this.props;

		if (error) {
			emitError({message: `${error}: ${error_description}`});
		} else if (code) {
			emitAuthCode({code});
		} else if (oauth_token && oauth_verifier) {
			emitToken({token: oauth_token, verifier: oauth_verifier});
		}
		/* eslint-enable camelcase */
	}

	render() {
		return (
			<Loading
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			/>
		);
	}
}
