/**
 * Copyright 2012 Liferay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.phidias.compile;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Date;

/**
 * @author Raymond Augé
 */
public class TPhLog {

	public PrintStream out;
	public String pattern = "%1$tH:%1$tM:%1$tS,%1$tL [%2$s:%3$d] %4$s %5$s\n";

	public boolean isEnabled() {
		return out != null;
	}

	public void log(Object... input) {
		if (out == null) {
			return;
		}

		StackTraceElement stackTraceElement =
			Thread.currentThread().getStackTrace()[2];

		StringWriter sw1 = new StringWriter();
		PrintWriter pw1 = new PrintWriter(sw1);

		StringWriter sw2 = new StringWriter();
		PrintWriter pw2 = new PrintWriter(sw2);

		for (Object element : input) {
			if (element instanceof Throwable) {
				pw2.println();

				((Throwable)element).printStackTrace(pw2);
			}
			else {
				pw1.print(' ');
				pw1.print(element);
			}
		}

		out.printf(
			pattern, new Date(), stackTraceElement.getClassName(),
			stackTraceElement.getLineNumber(), sw1.toString(), sw2.toString());
	}

}
