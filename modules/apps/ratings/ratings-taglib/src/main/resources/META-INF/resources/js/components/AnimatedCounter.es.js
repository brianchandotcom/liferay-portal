/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function usePrevious(value) {
	const ref = useRef();

	useEffect(() => {
		ref.current = value;
	});

	return ref.current;
}

function SlidingText({current, previous}) {
	const [animating, setAnimating] = useState(false);
	const direction = current > previous ? 'up' : 'down';
	const isMounted = useIsMounted();
	const maxLength = current.toString().length + 1;

	const finishAnimation = () => {
		if (isMounted) {
			setAnimating(false);
		}
	};

	useEffect(() => {
		if (
			Number.isInteger(current) &&
			Number.isInteger(previous) &&
			current !== previous
		) {
			setAnimating(true);
		}
	}, [current, previous]);

	return (
		<span
			className={classNames('animated-counter', {
				[`animating-${direction}`]: animating,
			})}
			onAnimationEnd={finishAnimation}
			style={{
				minWidth: `${maxLength}ch`,
			}}
		>
			<span className="current">{current}</span>
			{animating && <span className="previous">{previous}</span>}
		</span>
	);
}

function AnimatedCounter({counter}) {
	const prevCounter = usePrevious(counter);

	return <SlidingText current={counter} previous={prevCounter} />;
}

AnimatedCounter.propTypes = {
	counter: PropTypes.number.isRequired,
};

export default AnimatedCounter;
