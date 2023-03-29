@spira_Assets @Assets @team_FARO @priority_3
Feature: Assert 180 day time filter in Assets
    As a Business User, I should be able to change time filter to 180 days in assets

    Background: [Setup] Navigate to Assets page
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I go to the "Assets" page

    Scenario: Change time filter in Assets page
        Given I click the "Last 30 days" dropdown in the card
        When I click the "More Preset Periods" button
        And I click the "Last 180 days" button
        Then I should see time filter "Last 180 days" in card