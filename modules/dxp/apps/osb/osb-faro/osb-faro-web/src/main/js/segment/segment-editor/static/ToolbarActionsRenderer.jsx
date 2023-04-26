import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import Nav from 'shared/components/Nav';
import React from 'react';
import {noop} from 'lodash';
import {OrderedMap} from 'immutable';
import {PropTypes} from 'prop-types';

export default class ToolbarActionsRenderer extends React.Component {
	static defaultProps = {
		buttonDisplay: 'primary',
		onClick: noop,
		onSelectedClick: noop,
		onUndoChanges: noop,
		selectedItemsIOMap: new OrderedMap(),
		showAdded: false
	};

	static propTypes = {
		buttonDisplay: PropTypes.string,
		onClick: PropTypes.func,
		onSelectedClick: PropTypes.func,
		onUndoChanges: PropTypes.func,
		selectedItemsIOMap: PropTypes.instanceOf(OrderedMap),
		showAdded: PropTypes.bool
	};

	@autobind
	handleOnClick() {
		const {onClick, onSelectedClick, selectedItemsIOMap} = this.props;

		if (selectedItemsIOMap.isEmpty()) {
			onClick();
		} else {
			onSelectedClick(selectedItemsIOMap);
		}
	}

	@autobind
	handleUndoClick() {
		const {onUndoChanges, selectedItemsIOMap} = this.props;

		onUndoChanges(selectedItemsIOMap);
	}

	render() {
		const {
			buttonDisplay,
			className,
			selectedItemsIOMap,
			showAdded
		} = this.props;

		const itemsSelected = !selectedItemsIOMap.isEmpty();

		return (
			<Nav className={className}>
				<Nav.Item>
					{itemsSelected ? (
						<>
							<ClayButton
								className='button-root'
								displayType={buttonDisplay}
								onClick={this.handleUndoClick}
							>
								{Liferay.Language.get('undo-changes')}
							</ClayButton>

							{!showAdded && (
								<ClayButton
									className='button-root'
									displayType={buttonDisplay}
									onClick={this.handleOnClick}
								>
									{Liferay.Language.get('remove-members')}
								</ClayButton>
							)}
						</>
					) : (
						<ClayButton
							className='button-root'
							displayType={buttonDisplay}
							onClick={this.handleOnClick}
						>
							{Liferay.Language.get('add-members')}
						</ClayButton>
					)}
				</Nav.Item>
			</Nav>
		);
	}
}
