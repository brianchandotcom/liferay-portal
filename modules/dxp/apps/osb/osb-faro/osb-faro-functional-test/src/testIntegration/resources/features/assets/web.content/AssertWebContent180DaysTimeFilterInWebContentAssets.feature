@spira_Assets @Assets @team_FARO @priority_3
Feature: Assert Web Content 180 Days Time Filter In Web Content Assets Page

  Background: [Setup] Navigate to the main page
    * I go to the "Home" page
    * I login as "test@faro.io:test"
    * I should see the "Sites" page
    * I go to the "Assets" page
    * I click the "Web Content" tab

  Scenario: Set a 180 days time filter in Web Content assets page
    Given I click the "Web Content" tab
    And I click the "Last 30 days" dropdown in the card
    When I click the "More Preset Periods" button
    And I click the "Last 180 days" button
    Then I should see time filter "Last 180 days" in card