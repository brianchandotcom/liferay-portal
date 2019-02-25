import autobind from 'autobind-decorator';
import EntryItem from './EntryItem';
import Icon from '../Icon';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 * */
export default class Entries extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			selectedEntry: props.selectedEntry
		};
	}

	@autobind
	setEntry(entry) {
		const {onSelectEntry} = this.props;

		this.setState({selectedEntry: entry});
		onSelectEntry(entry);
	}

	render() {
		const {entries} = this.props;
		const {selectedEntry} = this.state;

		return (
			<div className="dropdown pagination-items-per-page">
				<a
					aria-expanded="false"
					aria-haspopup="true"
					className="dropdown-toggle"
					data-toggle="dropdown"
					href="#1"
					role="button"
				>
					{`${selectedEntry} ${Liferay.Language.get('entries')}`}
					<Icon iconName="caret-double-l" />
				</a>
				<div className="dropdown-menu dropdown-menu-top">
					{entries.map((entry, index) => (
						<EntryItem
							entry={entry}
							key={`${index}_${entry}`}
							onChangeEntry={this.setEntry}
						/>
					))}
				</div>
			</div>
		);
	}
}