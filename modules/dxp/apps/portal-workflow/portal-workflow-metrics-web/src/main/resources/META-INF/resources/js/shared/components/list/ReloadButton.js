import autobind from 'autobind-decorator';
import React from 'react';

export default class ReloadButton extends React.Component {
	@autobind
	reloadPage() {
		location.reload();
	}

	render() {
		return (
			<button className='btn btn-link btn-sm' onClick={this.reloadPage}>
				{Liferay.Language.get('reload-page')}
			</button>
		);
	}
}