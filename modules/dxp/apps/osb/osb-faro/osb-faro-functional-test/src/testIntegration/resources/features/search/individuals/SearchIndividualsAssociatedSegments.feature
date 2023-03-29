@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Search the Individual's Associated Segments List
	As an Business User, I should be able to search an Individual's Associated Segments List

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search an Individual's Associated Segments List
		Given I search for "Tory Glover"
		And I click "Tory Glover" in the table
		And I click the "Segments" tab
		When I search for "engineers"
		Then I should only see an Associated Segment named "engineers" in the table