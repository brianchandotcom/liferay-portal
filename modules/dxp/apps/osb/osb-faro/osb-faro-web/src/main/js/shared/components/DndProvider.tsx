import React from 'react';
import {DndProvider as BaseDndProvider, DndProviderProps} from 'react-dnd';

/**
 * Typed wrapper around react-dnd's DndProvider that explicitly accepts children.
 * react-dnd@11 declared DndProvider as React.FC without children (implicit in
 * React 17), which breaks under React 18 where FC no longer includes children.
 */
const DndProvider = BaseDndProvider as React.ComponentType<
	DndProviderProps<any, any> & {children?: React.ReactNode}
>;

export default DndProvider;
