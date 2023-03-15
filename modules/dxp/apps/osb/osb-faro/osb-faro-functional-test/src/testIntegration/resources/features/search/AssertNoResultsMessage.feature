@spira_Search @Search @List @team_FARO @priority_1
Feature: Search With No Results
	As an Business User, I should know when a search query returns no results

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Individuals Page
		Given I click "Individuals" in the sidebar
		And I click the "Known Individuals" tab
		When I search for "${Random.1}"
		Then I should see a message that there are no "Individuals" found

	Scenario: Segments Page
		Given I click "Segments" in the sidebar
		And I click the "Create Segment" button
		And I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		And I search for "Willa Watsica"
		And I click the checkbox on the table row containing "Willa Watsica"
		And I click the "Add" button
		And I name the Static segment "AssertNoResultsMessage - ${Random.2}" and save it
		And I go to the "Segments" page
		When I search for "${Random.1}"
		Then I should see a message that there are no "Segments" found

	Scenario: Data Source Page
		Given I go to the "Data Source" page
		When I search for "${Random.1}.csv"
		Then I should see a message that there are no "Data Sources" found