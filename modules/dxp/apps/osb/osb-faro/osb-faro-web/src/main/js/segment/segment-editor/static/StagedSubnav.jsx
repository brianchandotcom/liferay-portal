import autobind from 'autobind-decorator';
import Button from 'shared/components/Button';
import Label from 'shared/components/Label';
import React from 'react';
import SubnavTbar from 'shared/components/SubnavTbar';
import {noop} from 'lodash';
import {PropTypes} from 'prop-types';
import {sub} from 'shared/util/lang';

class StagedSubnav extends React.Component {
	static defaultProps = {
		onToggle: noop,
		showStaged: false,
		stagedMessage: sub(Liferay.Language.get('showing-only-selected-x'), [
			Liferay.Language.get('items').toLowerCase()
		])
	};

	static propTypes = {
		handleClearChecked: PropTypes.func,
		onToggle: PropTypes.func,
		onUndoChanges: PropTypes.func,
		selectedCountMessage: PropTypes.string.isRequired,
		showStaged: PropTypes.bool,
		stagedMessage: PropTypes.string,
		viewCurrentLinkText: PropTypes.string.isRequired,
		viewStagedLinkText: PropTypes.string.isRequired
	};

	@autobind
	handleUndoChanges() {
		const {handleClearChecked, onUndoChanges} = this.props;

		onUndoChanges({handleClearChecked});
	}

	render() {
		const {
			className,
			onToggle,
			onUndoChanges,
			selectedCountMessage,
			showStaged,
			stagedMessage,
			viewCurrentLinkText,
			viewStagedLinkText
		} = this.props;

		return (
			<SubnavTbar className={className}>
				<SubnavTbar.Item>
					{showStaged ? (
						<span>{stagedMessage}</span>
					) : (
						<Label display='success' size='lg' uppercase>
							{selectedCountMessage}
						</Label>
					)}
				</SubnavTbar.Item>

				<SubnavTbar.Item expand>
					<Button borderless display='unstyled' onClick={onToggle}>
						{showStaged ? viewCurrentLinkText : viewStagedLinkText}
					</Button>
				</SubnavTbar.Item>

				{showStaged && onUndoChanges && (
					<SubnavTbar.Item>
						<Button
							borderless
							display='unstyled'
							onClick={this.handleUndoChanges}
						>
							{Liferay.Language.get('undo-all')}
						</Button>
					</SubnavTbar.Item>
				)}
			</SubnavTbar>
		);
	}
}

export default StagedSubnav;
