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

package com.liferay.ant.build.exec;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.optional.Script;
import org.apache.tools.ant.types.Reference;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * @author Peter Yoo
 */
public class BeanshellTask extends Task {
	
	public void addText(String text) {
		_code = text;
	}
	
	@Override
	public void execute() throws BuildException {
		Project project = getProject();
		
		String beanshellType = project.getProperty("beanshell.type");
		
		if ("script".equals(beanshellType)) {
			executeScriptTask();
		}
		else {
			Interpreter interpreter = new Interpreter();
			
			interpreter.setClassLoader(this.getClass().getClassLoader());
			try {
				interpreter.set("project", getProject());
				
				interpreter.eval(_code);
			} 
			catch (EvalError ee) {
				throw new BuildException(ee);
			}
		}		
	}
	
	public void setClasspathref(String classpathRef) {
		_classpathRef = classpathRef;
	}

	protected void executeScriptTask() throws BuildException {
		Script scriptTag = new Script();
		
		scriptTag.setProject(getProject());
		
		scriptTag.setClasspathRef(new Reference(getProject(), _classpathRef));
		
		scriptTag.setLanguage("beanshell");
		
		scriptTag.addText(_code);
		
		scriptTag.perform();
	}
	
    private long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        //runtime.gc();
        return (runtime.totalMemory() - runtime.freeMemory())/1024;
    }	
	
	private String _classpathRef;	
	private String _code;
}

