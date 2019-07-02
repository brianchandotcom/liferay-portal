import React from 'react';

/**
 * LoadingState.
 * @extends React.Component
 */
export default class LoadingState extends React.Component {
	render() {
		return <span aria-hidden='true' className='loading-animation' />;
	}
}
