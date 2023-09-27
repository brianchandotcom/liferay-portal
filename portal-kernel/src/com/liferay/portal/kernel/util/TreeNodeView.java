/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Dennis Ju
 * @author Brian Wing Shun Chan
 */
public class TreeNodeView {

	public TreeNodeView(int id) {
		_id = id;
	}

	public void addChild(TreeNodeView treeNodeView) {
		_children.add(treeNodeView);
	}

	public List<TreeNodeView> getChildren() {
		return _children;
	}

	public int getDepth() {
		return _depth;
	}

	public String getHref() {
		return _href;
	}

	public long getId() {
		return _id;
	}

	public String getImg() {
		return _img;
	}

	public String getLs() {
		return _ls;
	}

	public String getName() {
		return _name;
	}

	public String getObjId() {
		return _objId;
	}

	public long getParentId() {
		return _parentId;
	}

	public boolean isLeaf() {
		return _leaf;
	}

	public void setChildren(List<TreeNodeView> children) {
		_children = children;
	}

	public void setDepth(int depth) {
		_depth = depth;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setImg(String img) {
		_img = img;
	}

	public void setLeaf(boolean leaf) {
		_leaf = leaf;
	}

	public void setLs(String ls) {
		_ls = ls;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setObjId(String objId) {
		_objId = objId;
	}

	public void setParentId(long parentId) {
		_parentId = parentId;
	}

	private List<TreeNodeView> _children = new ArrayList<>();
	private int _depth;
	private String _href = "javascript:void(0);";
	private long _id;
	private String _img = StringPool.BLANK;
	private boolean _leaf;
	private String _ls = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _objId = StringPool.BLANK;
	private long _parentId;

}