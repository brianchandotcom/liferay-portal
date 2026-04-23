import React from 'react';

export const OnboardingContext = React.createContext<{
	onboardingTriggered: boolean;
	setOnboardingTriggered: () => void;
}>({onboardingTriggered: false, setOnboardingTriggered: () => undefined});
