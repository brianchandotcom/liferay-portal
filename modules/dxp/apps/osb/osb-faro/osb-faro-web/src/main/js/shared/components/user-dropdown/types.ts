export type MenuItem = {
	active?: boolean;
	childMenuId?: string;
	divider?: boolean;
	externalLink?: boolean;
	icon?: string;
	iconAlignment?: string;
	label?: string;
	onClick?: () => void;
	url?: string;
};

export type MenuGroup = {
	items: MenuItem[];
	subheaderLabel?: string;
};

export type Menus = {[key: string]: MenuGroup[]};
