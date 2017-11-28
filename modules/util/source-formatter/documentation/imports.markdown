## Imports

- Do not use wildcards:

### Example

Incorrect:

```java
import com.liferay.portal.kernel.util.*;
```

Correct:

```java
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
```

- Import classes, not methods:

### Example

Incorrect:

```java
import static org.junit.Assert.assertTrue;

...

assertTrue(user.isActive());
```

Correct:

```java
import org.junit.Assert;

...

Assert.assertTrue(user.isActive());
```