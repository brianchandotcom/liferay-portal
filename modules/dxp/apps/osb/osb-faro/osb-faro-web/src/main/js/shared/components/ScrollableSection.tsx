import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {createRef} from 'react';
import {hasChanges} from 'shared/util/react';

const scrollBy = (ref: React.RefObject<HTMLElement>, val: number): void => {
	if (ref.current) {
		if (ref.current.scrollBy) {
			ref.current.scrollBy({
				behavior: 'smooth',
				left: val
			});
		} else {
			ref.current.scrollLeft += val;
		}
	}
};

const scrollTo = (ref: React.RefObject<HTMLElement>, val: number): void => {
	if (ref.current) {
		if (ref.current.scrollTo) {
			ref.current.scrollTo({
				behavior: 'smooth',
				left: val
			});
		} else {
			ref.current.scrollLeft = val;
		}
	}
};

interface IScrollableSectionProps extends React.HTMLAttributes<HTMLElement> {}

interface IScrollableSectionState {
	showScroll: boolean;
}

export default class ScrollableSection extends React.Component<
	IScrollableSectionProps,
	IScrollableSectionState
> {
	state = {
		showScroll: false
	};

	private _containerRef = createRef<HTMLDivElement>();

	componentDidMount() {
		window.addEventListener('resize', this.handleShowScroll);
	}

	componentDidUpdate(prevProps: IScrollableSectionProps) {
		if (
			hasChanges(
				prevProps as Record<string, unknown>,
				this.props as Record<string, unknown>,
				'children'
			)
		) {
			this.handleShowScroll();
		}
	}

	componentWillUnmount() {
		window.removeEventListener('resize', this.handleShowScroll);
	}

	@autobind
	handleShowScroll() {
		if (this._containerRef.current) {
			const {offsetWidth, scrollWidth} = this._containerRef.current;

			this.setState({showScroll: offsetWidth < scrollWidth});
		}
	}

	@autobind
	handleScrollLeft() {
		if (this._containerRef.current) {
			const {offsetWidth} = this._containerRef.current;

			scrollBy(this._containerRef, Number(`-${offsetWidth}`));
		}
	}

	@autobind
	handleScrollRight() {
		if (this._containerRef.current) {
			const {offsetWidth} = this._containerRef.current;

			scrollBy(this._containerRef, offsetWidth);
		}
	}

	/**
	 * Public method to scroll to beginning of container.
	 */
	@autobind
	scrollToBeg() {
		if (this._containerRef.current) {
			scrollTo(this._containerRef, 0);
		}
	}

	/**
	 * Public method to scroll to end of container.
	 */
	@autobind
	scrollToEnd() {
		if (this._containerRef.current) {
			const {scrollWidth} = this._containerRef.current;

			scrollTo(this._containerRef, scrollWidth);
		}
	}

	render() {
		const {
			props: {children},
			state: {showScroll}
		} = this;

		return (
			<div className='scrollable-section-root d-inline-flex'>
				{showScroll && (
					<div className='scroll-back-container d-flex align-items-center'>
						<ClayButton
							aria-label={Liferay.Language.get('scroll-left')}
							borderless
							className='button-root'
							displayType='secondary'
							monospaced
							onClick={this.handleScrollLeft}
							size='sm'
						>
							<ClayIcon
								className='icon-root'
								symbol='angle-left-small'
							/>
						</ClayButton>
					</div>
				)}

				<div
					className={getCN('scroll-container', {
						scrollable: showScroll
					})}
					ref={this._containerRef}
				>
					{children}
				</div>

				{showScroll && (
					<div className='scroll-forward-container d-flex align-items-center'>
						<ClayButton
							aria-label={Liferay.Language.get('scroll-right')}
							borderless
							className='button-root'
							displayType='secondary'
							monospaced
							onClick={this.handleScrollRight}
							size='sm'
						>
							<ClayIcon
								className='icon-root'
								symbol='angle-right-small'
							/>
						</ClayButton>
					</div>
				)}
			</div>
		);
	}
}
