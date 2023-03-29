@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Chart ticks for pages Visitor Behavior standardized
    As a Business User, I should be able to see the chart ticks for pages Visitor Behavior

    Background: [Setup] Navigate to Pages
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page

    Scenario Outline: Visitor Behavior hour format
        Given I go to the "Sites" page
        And I click the "Pages" tab
        And I click "mesh synergistic schemas" in the table
        When I click the "Last 30 days" dropdown in the "Visitors Behavior" card
        And I click the "More Preset Periods" button
        And I click the "<dateRange>" button
        And I should see standardized granularity of "h a"

        Examples:
            |         dateRange         |
            |        Last 24 hours      |
            |          Yesterday        |

    Scenario Outline: Visitors Behavior day, week, month format
        Given I go to the "Sites" page
        And I click the "Pages" tab
        And I click "mesh synergistic schemas" in the table
        When I click the "Last 30 days" dropdown in the "Visitors Behavior" card
        And I click the "More Preset Periods" button
        And I click the "<dateRange>" button
        Then I should see standardized granularity of "<pattern>"

        Examples:
            |      dateRange     |  pattern  |
            |    Last 7 days     |   MMM d   |
            |    Last 28 days    |   MMM d   |
            |    Last 30 days    |   MMM d   |
            |    Last 90 days    |   MMM d   |
            |    Last 180 days   |   MMM d   |
            |    Last Year       |   MMM d   |