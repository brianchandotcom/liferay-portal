import Component from 'metal-component';
import Soy from 'metal-soy';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './SegmentSelector.soy';
import {
	CHANGE_SEGMENT_ID
} from '../../actions/actions.es';

class SegmentSelector extends Component {

	/**
	 * @private
	 * @review
	 */
	_handleSegmentChange(event) {
		const value = event.target.value;
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
		'classPK',
		'segmentId',
		'segments'
	]
);

Soy.register(ConnectedSegmentSelector, templates);

export {ConnectedSegmentSelector};
export default ConnectedSegmentSelector;