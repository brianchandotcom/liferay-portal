import w from '../utils/window.es';

import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { bindAll } from '../utils/utils.es';

class InfiniteScroll extends Component {
	constructor(props) {
		super(props);

		bindAll(
			this,
			'handleScroll_',
			'shouldScroll_'
		);

		const {leading, maxWait, wait} = this.props;

		this.debouncedScrollHandler_ = _.debounce(this.handleScroll_, wait, {leading, maxWait});
	}

	componentDidMount() {
		this.attachScrollHandler_();

		if (this.props.hasMoreResults) {
			this.debouncedScrollHandler_();
		}
	}

	componentWillUnmount() {
		if (this._request) {
			this._request.cancel();
		}

		this.debouncedScrollHandler_.cancel();

		this.detachScrollHandler_();
	}

	attachScrollHandler_(forceAttach) {
		const {attachToElement, hasMoreResults} = this.props;

		if (hasMoreResults || forceAttach) {
			attachToElement().addEventListener('scroll', this.debouncedScrollHandler_);
		}
	}

	detachScrollHandler_() {
		this.props.attachToElement().removeEventListener('scroll', this.debouncedScrollHandler_);
	}

	handleScroll_() {
		const {onScrollEnd} = this.props;

		if (!this.state.loading_ && onScrollEnd && this.shouldScroll_()) {
			this.state.loading_ = true;

			this._request = onScrollEnd().then(
				() => {
					this.state.loading_ = false;
				}
			);
		}
	}

	shouldScroll_() {
		let shouldScroll = false;

		const scrollContainer = this.element;

		if (scrollContainer && scrollContainer.offsetParent) {
			shouldScroll = (scrollContainer.getBoundingClientRect().bottom - w.innerHeight - this.props.scrollOffset) < 0;
		}

		return shouldScroll;
	}

	syncHasMoreResults(value) {
		if (value) {
			this.attachScrollHandler_(value);
		}
		else {
			this.debouncedScrollHandler_.cancel();

			this.detachScrollHandler_();
		}
	}

	render() {
		return <div>{this.props.children}</div>;
	}
}

InfiniteScroll.defaultProps = {
	attachToElement: () => w,
	hasMoreResults: true,
	leading: false,
	maxWait: 200,
	scrollOffset: 0,
	wait: 100,
	loading_: false
};

InfiniteScroll.propTypes = {
	attachToElement: PropTypes.func,
	hasMoreResults: PropTypes.bool,
	leading: PropTypes.bool,
	maxWait: PropTypes.number,
	onScrollEnd: PropTypes.func,
	scrollOffset: PropTypes.number,
	wait: PropTypes.number
};

export default InfiniteScroll;
