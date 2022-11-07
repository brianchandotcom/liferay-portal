import { ILayoutDataItemType } from "../app/config/constants/layoutDataItemTypes";

export interface IDeletedLayoutDataItem {
	itemId: string;
	portletIds: string[];
	position: number;
	childrenItemIds: string[];
}

export interface ILayoutDataItem {
	children: string[];
	config: Record<string, unknown>
	itemId: string;
	itemType: ILayoutDataItemType,
	parentId: string,
}

export interface ILayoutData {
	deletedItems: IDeletedLayoutDataItem[];
	items: Record<string, ILayoutDataItem>;
	rootItems: { dropZone: string; main: string; };
	version: string;
}