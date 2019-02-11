import autobind from 'autobind-decorator';
import Icon from '../../shared/components/Icon';
import ProcessListEntryItem from './ProcessListEntryItem';
import React from 'react';

export default class ProcessListEntries extends React.Component {
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
						<ProcessListEntryItem
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