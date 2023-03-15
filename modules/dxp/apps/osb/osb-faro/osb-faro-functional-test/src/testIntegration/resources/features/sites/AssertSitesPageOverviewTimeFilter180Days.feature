@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Page Overview time filter changes to 180 days
    As a Business User, I should be able to change time filter to 180 days

    Background: [Setup] Navigate to Page Overview
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click the "Pages" tab
        * I click "mesh synergistic schemas" in the table

    Scenario: Change time filter in Page Overview
        Given I click the "Last 30 days" dropdown in the "Visitors Behavior" card
        When I click the "More Preset Periods" button
        And I click the "Last 180 days" button
        Then I should see "180" columns in the bar graph table