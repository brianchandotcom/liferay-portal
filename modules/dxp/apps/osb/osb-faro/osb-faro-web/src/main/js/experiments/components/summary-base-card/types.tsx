import React from 'react';

export type Modal = {
	Component: any;
	props: Object;
	title?: string;
};

export type Alert = {
	description?: string;
	symbol?: string;
	title: string;
};

export type ButtonProps = {
	['data-tooltip']?: boolean;
	disabled?: boolean;
	symbol?: string;
	title?: string;
};

export type Description = {
	className: string;
};

export type Header = {
	modals?: Array<Modal>;
	cardModals?: Array<Modal>;
	Description?: React.FC;
	title: string;
};

export type Setup = {
	current: number;
	steps: Array<Step>;
	subtitle?: string;
	title: string;
};

export type Summary = {
	description?: string;
	subtitle?: string;
	title: string;
};

export type Status =
	| 'completed'
	| 'draft'
	| 'finished'
	| 'running'
	| 'scheduled'
	| 'terminated';

export type Step = {
	modal?: Modal;
	buttonProps: ButtonProps;
	Description: React.FC<Description>;
	experimentLink?: string;
	link?: string;
	title: string;
};
