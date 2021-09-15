/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// DWB20: ThreadIO should check and reset IO if something (e.g. jetty) overrides
package org.apache.felix.gogo.runtime.threadio;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.felix.service.threadio.ThreadIO;
import org.osgi.annotation.bundle.Capability;
import org.osgi.namespace.service.ServiceNamespace;

@Capability(
    namespace = ServiceNamespace.SERVICE_NAMESPACE,
    attribute = "objectClass='org.apache.felix.service.threadio.ThreadIO'"
)
public class ThreadIOImpl implements ThreadIO
{
    final Marker defaultMarker = new Marker(System.in, System.out, System.err, null);
    final ThreadPrintStream err = new ThreadPrintStream(this, System.err, true);
    final ThreadPrintStream out = new ThreadPrintStream(this, System.out, false);
    final ThreadInputStream in = new ThreadInputStream(this, System.in);
    final ThreadLocal<Deque<Marker>> current = new InheritableThreadLocal<Deque<Marker>>()
    {
        @Override
        protected Deque<Marker> childValue(Deque<Marker> markers) {
            return new LinkedList<Marker>(markers);
        }

        @Override
        protected Deque<Marker> initialValue()
        {
            return new LinkedList<Marker>();
        }
    };

    public void start()
    {
    }

    public void stop()
    {
    }

    Marker current()
    {
        Deque<Marker> markers = current.get();

        while (true) {
            Marker marker = markers.peek();

            if (marker == null) {
                current.remove();

                return defaultMarker;
            }

            if (marker.deactivated) {
                markers.pop();
            }
            else {
                return marker;
            }
        }
    }

    public void close()
    {
        Deque<Marker> markers = current.get();

        Marker marker = markers.pop();

        marker.deactivate();

        if (markers.isEmpty()) {
            current.remove();

            System.setOut(this.out);
            System.setIn(this.in);
            System.setErr(this.err);
        }
    }

    public void setStreams(InputStream in, PrintStream out, PrintStream err)
    {
        assert in != null;
        assert out != null;
        assert err != null;

        Deque<Marker> markers = current.get();

        Marker previousMarker = null;

        if (markers.isEmpty()) {
            previousMarker = defaultMarker;

            System.setErr(this.err);
            System.setIn(this.in);
            System.setOut(this.out);
        }
        else {
            previousMarker = markers.peek();
        }

        if (in == this.in) {
            in = previousMarker.in;
        }

        if (out == this.out) {
            out = previousMarker.out;
        }

        if (err == this.err) {
            err = previousMarker.err;
        }

        markers.push(new Marker(in, out, err, null));
    }
}
/* @generated */