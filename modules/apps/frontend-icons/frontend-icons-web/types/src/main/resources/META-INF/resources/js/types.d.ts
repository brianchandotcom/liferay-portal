export interface IIconPack {
	editable?: boolean;
	icons: Array<{
		name: string;
	}>;
}
export interface IIconPacks {
	[key: string]: IIconPack;
}
