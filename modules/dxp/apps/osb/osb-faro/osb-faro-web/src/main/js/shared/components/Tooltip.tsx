import dom from 'metal-dom';
import getCN from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
import {Align} from 'metal-position';
import {CSSTransition, TransitionGroup} from 'react-transition-group';
import {EventHandler} from 'metal-events';

const TOOLTIP_HOVER_DELAY = 600;

const ALIGNMENTS = [
	'top',
	'top-right',
	'right',
	'bottom-right',
	'bottom',
	'bottom-left',
	'left',
	'top-left'
];

const ALIGNMENTS_MAP = {
	bottom: Align.Bottom,
	'bottom-left': Align.BottomLeft,
	'bottom-right': Align.BottomRight,
	left: Align.Left,
	right: Align.Right,
	top: Align.Top,
	'top-left': Align.TopLeft,
	'top-right': Align.TopRight
};

type Alignments = typeof ALIGNMENTS_MAP[keyof typeof ALIGNMENTS_MAP];

interface ITooltipProps extends React.HTMLAttributes<HTMLDivElement> {
	initialAlignment: Alignments;
	message: string;
	target: HTMLElement;
}

const Tooltip: React.FC<ITooltipProps> = ({
	className,
	initialAlignment = ALIGNMENTS[0],
	message,
	target,
	...otherProps
}) => {
	const [alignment, setAlignment] = useState<Alignments>(initialAlignment);

	const _elementRef = useRef();

	useEffect(() => {
		window.addEventListener('resize', alignOverlay);
		window.addEventListener('scroll', alignOverlay);

		alignOverlay();

		return () => {
			window.removeEventListener('resize', alignOverlay);
			window.removeEventListener('scroll', alignOverlay);
		};
	}, []);

	useEffect(() => {
		if (target) {
			const {x, y} = target.getBoundingClientRect();

			if (!!x && !!y) {
				alignOverlay();
			}
		}
	}, [message, target]);

	const alignOverlay = () => {
		const newAlignment =
			ALIGNMENTS[
				Align.align(
					_elementRef.current,
					target,
					ALIGNMENTS_MAP[alignment]
				)
			];

		if (newAlignment !== alignment) {
			setAlignment(newAlignment);
		}
	};

	const classes = getCN('show', 'tooltip', 'tooltip-root', className, {
		[`clay-tooltip-${alignment}`]: alignment
	});

	return (
		<div
			{...otherProps}
			className={classes}
			ref={_elementRef}
			role='tooltip'
		>
			<div className='arrow' />

			<div className='tooltip-inner'>{message}</div>
		</div>
	);
};

const TooltipBase: React.FC = () => {
	const [alignment, setAlignment] = useState<Alignments>(ALIGNMENTS_MAP.top);
	const [message, setMessage] = useState<string>('');
	const [show, setShow] = useState<boolean>(false);
	const [target, setTarget] = useState<HTMLElement>();

	const _eventHandler = new EventHandler();

	let _responseMessage = '';
	let _timeout: ReturnType<typeof setTimeout>;

	useEffect(() => {
		setTriggers();

		return () => {
			if (_timeout) {
				clearTimeout(_timeout);
			}

			_eventHandler.removeAllListeners();
		};
	}, []);

	const handleClick = (event: PointerEvent) => {
		if (_responseMessage) {
			setMessage(_responseMessage);
			setShow(true);
		} else {
			handleHide(event);
		}
	};

	const handleHide = (event: PointerEvent) => {
		const target = event.target as HTMLElement;

		const dataTitle = target && target.getAttribute('data-title');

		if (dataTitle) {
			target.removeEventListener('click', handleClick);

			target.setAttribute('title', dataTitle);

			target.removeAttribute('data-title');
		}

		setShow(false);

		clearTimeout(_timeout);
	};

	const handleShow = (event: PointerEvent) => {
		const target = event.target as HTMLElement;

		_responseMessage = target.getAttribute('data-tooltip-response');

		const align = target.getAttribute('data-tooltip-align');

		target.addEventListener('click', handleClick);

		const message = target.getAttribute('title');

		if (message) {
			target.setAttribute('data-title', message);
			target.removeAttribute('title');

			setAlignment(align || 'top');
			setMessage(message);
			setTarget(target);

			if (
				target.hasAttribute('data-tooltip') ||
				target.hasAttribute('data-tooltip-response')
			) {
				_timeout = setTimeout(() => {
					setShow(true);
				}, TOOLTIP_HOVER_DELAY);
			}
		}
	};

	const setTriggers = () => {
		_eventHandler.removeAllListeners();

		_eventHandler.add(
			dom.delegate(document, 'mouseenter', '[title]', handleShow),
			dom.delegate(document, 'mouseleave', '[data-title]', handleHide)
		);
	};

	return (
		<TransitionGroup>
			{show && (
				<CSSTransition
					appear
					classNames='transition-fade-in-out'
					timeout={{enter: 150, exit: 150}}
				>
					<Tooltip
						initialAlignment={alignment}
						message={message}
						target={target}
					/>
				</CSSTransition>
			)}
		</TransitionGroup>
	);
};

export default TooltipBase;
