## AttributeOrderCheck

Attributes in anonymous class should be sorted.

### Example

Incorrect:

```
App app = new App() {
    {
        dataDefinitionId = "";
        appDeployments = new AppDeployment[] {
            new AppDeployment() {
                {
                    type = "productMenu";
                    settings = HashMapBuilder.<String, Object>put(
                        "scope", new String[] {"control_panel"}
                    ).build();
                }
            },
            new AppDeployment() {
                {
                    type = "widget";
                    settings = new HashMap<>();
                }
            }
        };
    }
};
```

Correct:

```
App app = new App() {
    {
        appDeployments = new AppDeployment[] {
            new AppDeployment() {
                {
                    settings = HashMapBuilder.<String, Object>put(
                        "scope", new String[] {"control_panel"}
                    ).build();
                    type = "productMenu";
                }
            },
            new AppDeployment() {
                {
                    settings = new HashMap<>();
                    type = "widget";
                }
            }
        };
        dataDefinitionId = "";
    }
};
```