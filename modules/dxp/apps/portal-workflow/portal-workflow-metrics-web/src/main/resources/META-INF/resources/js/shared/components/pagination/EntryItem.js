import autobind from 'autobind-decorator';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 * */
export default class EntryItem extends React.Component {
	@autobind
	setEntry() {
		const {entry, onChangeEntry} = this.props;

		onChangeEntry(entry);
	}

	render() {
		const {entry} = this.props;

		return (
			<a className="dropdown-item" href={`#${entry}`} onClick={this.setEntry}>
				{entry}
			</a>
		);
	}
}