import Component from 'metal-component';
import Soy from 'metal-soy';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './SegmentSelector.soy';
import {CHANGE_SEGMENT_ID} from '../../actions/actions.es';

/**
 * SegmentSelector
 */
class SegmentSelector extends Component {

	/**
	 * Prepares availableSegments to be consume by selector
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		const {availableSegments} = state;
		const segments = Object.keys(availableSegments)
			.map(
				key => (
					{
						id: availableSegments[key].segmentId,
						label: availableSegments[key].segmentLabel
					}
				)
			);

		return Object.assign({}, state, {segments});
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleSegmentChange(event) {
		const {value} = event.target;

		this.store.dispatchAction(
			CHANGE_SEGMENT_ID,
			{
				segmentId: value
			}
		);
	}

}

const ConnectedSegmentSelector = getConnectedComponent(
	SegmentSelector,
	[
		'availableSegments',
		'classPK',
		'portletNamespace',
		'segmentId'
	]
);

Soy.register(ConnectedSegmentSelector, templates);

export {ConnectedSegmentSelector};
export default ConnectedSegmentSelector;