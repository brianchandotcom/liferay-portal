import React from 'react';

export default class ReloadButton extends React.Component {
	reloadPage() {
		location.reload();
	}

	render() {
		return (
			<button
				className='btn btn-link btn-sm'
				onClick={this.reloadPage.bind(this)}
			>
				{Liferay.Language.get('reload-page')}
			</button>
		);
	}
}
